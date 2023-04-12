package vision.cotegory.parser.baekjoon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Tag;
import vision.cotegory.parser.baekjoon.dto.solvedac.SolvedAcTagDto;
import vision.cotegory.parser.baekjoon.dto.solvedac.SolvedAcTitleDto;
import vision.cotegory.parser.baekjoon.entity.BaekjoonProblem;
import vision.cotegory.parser.baekjoon.entity.BaekjoonTag;
import vision.cotegory.parser.baekjoon.repository.BaekjoonProblemRepository;
import vision.cotegory.parser.baekjoon.repository.BaekjoonTagRepository;
import vision.cotegory.parser.baekjoon.util.BaekjoonProblemListParser;
import vision.cotegory.parser.baekjoon.util.BaekjoonProblemParser;
import vision.cotegory.parser.baekjoon.util.SolvedAcWebClient;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class BaekjoonService {

    private final SolvedAcWebClient solvedacWebClient;
    private final BaekjoonProblemRepository baekjoonProblemRepository;
    private final BaekjoonTagRepository baekjoonTagRepository;

    public void updateAll() {
        baekjoonProblemRepository.deleteAll();

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
                .forEach(problemDto -> {
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

                    final BaekjoonTag baekjoonTag = BaekjoonTag.builder()
                            .tags(tags)
                            .problemNumber(problemDto.getProblemId())
                            .build();

                    BaekjoonTag savedBaekjoonTag = baekjoonTagRepository.save(baekjoonTag);
                    log.info("[saveTag]{}번({}):{}", savedBaekjoonTag.getProblemNumber(), problemDto.getTitleKo(), savedBaekjoonTag.getTags());
                });
    }

    private void crawlAllProblemBySavedTag() {
        baekjoonTagRepository.getAllProblemNumbers().stream().parallel()
                .forEach(this::createBaekjoonProblemDto);
    }

    private List<Integer> getPages(Tag tag) {
        return new BaekjoonProblemListParser(tag.toBaekjoonCode()).getProblemPages();
    }

    private List<Integer> getNumbers(Tag tag, Integer page) {
        return new BaekjoonProblemListParser(tag.toBaekjoonCode(), page).getProblemNumbers();
    }

    private BaekjoonProblem createBaekjoonProblemDto(Integer problemNumber) {
        BaekjoonProblemParser baekjoonProblemParser = new BaekjoonProblemParser(problemNumber);
        if(baekjoonProblemParser.getTimeLimit() == 0)
            return null;

        BaekjoonProblem baekjoonProblem = BaekjoonProblem.builder()
                .problemNumber(baekjoonProblemParser.getProblemNumber())
                .title(baekjoonProblemParser.getTitle())
                .timeLimit(baekjoonProblemParser.getTimeLimit())
                .memoryLimit(baekjoonProblemParser.getMemoryLimit())
                .subMissionCount(baekjoonProblemParser.getSubmissionCount())
                .correctAnswerCount(baekjoonProblemParser.getCorrectAnswerCount())
                .correctUserCount(baekjoonProblemParser.getCorrectUserCount())
                .correctRate(baekjoonProblemParser.getCorrectRate())
                .problemInput(baekjoonProblemParser.getProblemInput())
                .problemOutput(baekjoonProblemParser.getProblemOutput())
                .problemBody(baekjoonProblemParser.getProblemBody())
                .sampleInput(baekjoonProblemParser.getSampleInput())
                .sampleOutput(baekjoonProblemParser.getSampleOutput())
                .build();

        final BaekjoonProblem savedBaekjoonProblem = baekjoonProblemRepository.save(baekjoonProblem);
        log.info("[saveProblem]{}번({})", savedBaekjoonProblem.getProblemNumber(), savedBaekjoonProblem.getTitle());
        return savedBaekjoonProblem;
    }
}
