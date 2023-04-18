package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vision.cotegory.entity.*;
import vision.cotegory.entity.info.BaekjoonProblemInfo;
import vision.cotegory.entity.info.BaekjoonProblemPage;
import vision.cotegory.util.baekjoon.dto.SolvedAcProblemDto;
import vision.cotegory.util.baekjoon.dto.SolvedAcTagDto;
import vision.cotegory.util.baekjoon.dto.SolvedAcTitleDto;
import vision.cotegory.repository.BaekjoonPageRepository;
import vision.cotegory.repository.BaekjoonTagRepository;
import vision.cotegory.util.baekjoon.BaekjoonPageListCrawler;
import vision.cotegory.util.baekjoon.BaekjoonPageCrawler;
import vision.cotegory.util.baekjoon.SolvedAcWebClient;
import vision.cotegory.repository.QuizRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class BaekjoonService {

    private final SolvedAcWebClient solvedacWebClient;
    private final BaekjoonPageRepository baekjoonPageRepository;
    private final BaekjoonTagRepository baekjoonTagRepository;
    private final TagGroupConst tagGroupConst;
    private final QuizRepository quizRepository;

    public void updateAll() {
        baekjoonPageRepository.deleteAll();
        baekjoonTagRepository.deleteAll();

        Stream.of(Tag.values()).forEach(tag -> {
            if (tag.equals(Tag.OTHERS))
                return;
            crawlAllTagParallelByTag(tag);
        });
        crawlAllProblemBySavedTag();
    }

    private void crawlAllTagParallelByTag(Tag tag) {
        getPages(tag).stream()
                .map(page -> getNumbers(tag, page))
                .flatMap(numbers -> solvedacWebClient.getSolvedAcProblemDtosByProblemNumbers(numbers).stream())
                .forEach(this::extractTagFromDto);
    }

    private void extractTagFromDto(SolvedAcProblemDto problemDto) {
        if (baekjoonTagRepository.existsByProblemNumber(problemDto.getProblemId()))
            return;
        if (problemDto.getTitles().stream().map(SolvedAcTitleDto::getLanguage).noneMatch(e -> e.equals("ko")))
            return;
        if (problemDto.getTags().isEmpty())
            return;

        Set<Tag> tags = problemDto.getTags().stream()
                .map(SolvedAcTagDto::getBojTagId)
                .map(Tag::of)
                .collect(Collectors.toSet());

        final Map<TagGroup, Tag> assignableGroups = tagGroupConst.assignableGroups(tags);
        if (assignableGroups.isEmpty())
            return;

        final BaekjoonProblemInfo baekjoonInfo = BaekjoonProblemInfo.builder()
                .tags(tags)
                .level(problemDto.getLevel())
                .problemNumber(problemDto.getProblemId())
                .build();
        BaekjoonProblemInfo savedBaekjoonInfo = baekjoonTagRepository.save(baekjoonInfo);

        assignableGroups.forEach((tagGroup, tag) -> {
            Quiz quiz = Quiz.builder()
                    .problemInfo(savedBaekjoonInfo)
                    .tagGroup(tagGroup)
                    .answerTag(tag)
                    .build();
            quizRepository.save(quiz);
        });

        log.info("[saveTag]{}번({}):{} | {}", savedBaekjoonInfo.getProblemNumber(), problemDto.getTitleKo(), savedBaekjoonInfo.getTags(), assignableGroups);
    }

    private void crawlAllProblemBySavedTag() {
        baekjoonTagRepository.getAllProblemNumbers().stream().parallel()
                .forEach(this::createBaekjoonProblemDto);
    }

    private List<Integer> getPages(Tag tag) {
        return new BaekjoonPageListCrawler(tag.toBaekjoonCode()).getProblemPages();
    }

    private List<Integer> getNumbers(Tag tag, Integer page) {
        return new BaekjoonPageListCrawler(tag.toBaekjoonCode(), page).getProblemNumbers();
    }

    private BaekjoonProblemPage createBaekjoonProblemDto(Integer problemNumber) {
        BaekjoonPageCrawler baekjoonPageCrawler = new BaekjoonPageCrawler(problemNumber);
        if (baekjoonPageCrawler.getTimeLimit() == 0)
            return null;

        BaekjoonProblemPage baekjoonPage = BaekjoonProblemPage.builder()
                .problemNumber(baekjoonPageCrawler.getProblemNumber())
                .title(baekjoonPageCrawler.getTitle())
                .timeLimit(baekjoonPageCrawler.getTimeLimit())
                .memoryLimit(baekjoonPageCrawler.getMemoryLimit())
                .subMissionCount(baekjoonPageCrawler.getSubmissionCount())
                .correctAnswerCount(baekjoonPageCrawler.getCorrectAnswerCount())
                .correctUserCount(baekjoonPageCrawler.getCorrectUserCount())
                .correctRate(baekjoonPageCrawler.getCorrectRate())
                .problemInput(baekjoonPageCrawler.getProblemInput())
                .problemOutput(baekjoonPageCrawler.getProblemOutput())
                .problemBody(baekjoonPageCrawler.getProblemBody())
                .sampleInput(baekjoonPageCrawler.getSampleInput())
                .sampleOutput(baekjoonPageCrawler.getSampleOutput())
                .build();

        final BaekjoonProblemPage savedBaekjoonPage = baekjoonPageRepository.save(baekjoonPage);
        log.info("[saveProblem]{}번({})", savedBaekjoonPage.getProblemNumber(), savedBaekjoonPage.getTitle());
        return savedBaekjoonPage;
    }
}
