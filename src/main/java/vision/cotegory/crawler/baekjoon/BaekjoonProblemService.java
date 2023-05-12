package vision.cotegory.crawler.baekjoon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vision.cotegory.crawler.baekjoon.SolvedAcProblemDto.SolvedAcTagDto;
import vision.cotegory.crawler.baekjoon.SolvedAcProblemDto.SolvedAcTitleDto;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Tag;
import vision.cotegory.entity.TagGroup;
import vision.cotegory.entity.TagGroupConst;
import vision.cotegory.entity.problem.BaekjoonProblem;
import vision.cotegory.entity.problem.BaekjoonProblemPage;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.repository.BaekjoonPageRepository;
import vision.cotegory.repository.BaekjoonProblemRepository;
import vision.cotegory.repository.QuizRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class BaekjoonProblemService {

    private final SolvedAcWebClient solvedacWebClient;
    private final BaekjoonPageRepository baekjoonPageRepository;
    private final BaekjoonProblemRepository baekjoonProblemRepository;
    private final TagGroupConst tagGroupConst;
    private final QuizRepository quizRepository;

    public void updateAll() {
        quizRepository.deleteAll();
        baekjoonPageRepository.deleteAll();
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
                .forEach(this::extractTagFromDto);
    }

    private void extractTagFromDto(SolvedAcProblemDto problemDto) {
        if (baekjoonProblemRepository.existsByProblemNumber(problemDto.getProblemId()))
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

        BaekjoonProblem baekjoonProblem = BaekjoonProblem.builder()
                .tags(tags)
                .level(problemDto.getLevel())
                .problemNumber(problemDto.getProblemId())
                .build();
        BaekjoonProblem savedBaekjoonProblem = baekjoonProblemRepository.save(baekjoonProblem);

        assignableGroups.forEach((tagGroup, tag) -> {
            Quiz quiz = Quiz.builder()
                    .problem(savedBaekjoonProblem)
                    .tagGroup(tagGroup)
                    .answerTag(tag)
                    .activated(true)
                    .build();
            quizRepository.save(quiz);
        });

        log.info("[saveTag]{}번({}):{} | {}", savedBaekjoonProblem.getProblemNumber(), problemDto.getTitleKo(), savedBaekjoonProblem.getTags(), assignableGroups);
    }

    private void crawlAllProblemBySavedTag() {
        List<Long> deleteTargetProblems = new Vector<>();
        baekjoonProblemRepository.getAllProblemNumbers().stream().parallel()
                .forEach(number -> createBaekjoonProblemDto(number, deleteTargetProblems));
        log.info("[deleteProblems] {}개의 problem이 조건 불충족으로 삭제됩니다", deleteTargetProblems.size());
        deleteProblems(deleteTargetProblems);
        log.info("[deleteProblems] {}개 problem 삭제 완료", deleteTargetProblems.size());
    }

    private void deleteProblems(List<Long> deleteTargetProblemIds) {
        for (var deleteTargetProblemId : deleteTargetProblemIds) {
            BaekjoonProblem deleteTargetProblem = baekjoonProblemRepository.findById(deleteTargetProblemId)
                    .orElseThrow(NotExistEntityException::new);
            Integer tmpProblemNumber = deleteTargetProblem.getProblemNumber();

            quizRepository.deleteAll(quizRepository.findAllByProblem(deleteTargetProblem));
            baekjoonPageRepository.findByProblemNumber(deleteTargetProblem.getProblemNumber())
                            .ifPresent(baekjoonPageRepository::delete);
            baekjoonProblemRepository.delete(deleteTargetProblem);
            log.info("[deleteComplete]{}번 삭제 완료", tmpProblemNumber);
        }
    }

    private List<Integer> getPages(Tag tag) {
        return new BaekjoonPageListCrawler(tag.toBaekjoonCode()).getProblemPages();
    }

    private List<Integer> getNumbers(Tag tag, Integer page) {
        return new BaekjoonPageListCrawler(tag.toBaekjoonCode(), page).getProblemNumbers();
    }

    private BaekjoonProblemPage createBaekjoonProblemDto(Integer problemNumber, List<Long> deleteTargetProblems) {
        BaekjoonPageCrawler baekjoonPageCrawler = new BaekjoonPageCrawler(problemNumber);
        BaekjoonProblem baekjoonProblem = baekjoonProblemRepository.findByProblemNumber(problemNumber)
                .orElseThrow(NotExistEntityException::new);

        if (baekjoonPageCrawler.getTimeLimit() == 0) {
            log.info("[deleteProblemReserved]{}번 문제가 삭제될 예정입니다", baekjoonProblem.getProblemNumber());
            deleteTargetProblems.add(baekjoonProblem.getId());
            return null;
        }

        BaekjoonProblemPage baekjoonPage = BaekjoonProblemPage.builder()
                .baekjoonProblem(baekjoonProblem)
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
