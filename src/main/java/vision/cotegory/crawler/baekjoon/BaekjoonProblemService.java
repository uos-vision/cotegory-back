package vision.cotegory.crawler.baekjoon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
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
import vision.cotegory.repository.TagGroupRepository;

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
    private final PlatformTransactionManager transactionManager;
    private final TagGroupRepository tagGroupRepository;

    public void updateAll() {
        baekjoonPageRepository.deleteAll();

        Stream.of(Tag.values()).forEach(tag -> {
            if (tag.equals(Tag.OTHERS))
                return;
            crawlAllTagParallelByTag(tag);
        });
        crawlAllProblemBySavedTag();

        logQuizByTagGroups();
        logProblems();
    }

    public void logQuizByTagGroups() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {
            tagGroupRepository.findAll().forEach(tagGroup -> tagGroup.getTags().forEach(answerTag -> {
                Long cnt = quizRepository.countAllByTagGroupAndAnswerTag(tagGroup, answerTag);
                log.info("그룹:{} | 정답태그:{} | {}개", tagGroup.getName(), answerTag.toKorean(), cnt);
            }));
            return null;
        });
    }

    void logProblems(){
        final List<BaekjoonProblem> baekjoonProblems = baekjoonProblemRepository.findAll();
        long cnt = 0L;
        for(var baekjoonProblem : baekjoonProblems){
            List<Quiz> quizzes = quizRepository.findAllByProblem(baekjoonProblem);
            if(quizzes.isEmpty())
                ++cnt;
        }
        log.info("[notHaveQuizTotalCnt]{}개의 문제중 {}개의 Problem은 Quiz를 가지지 않습니다", baekjoonProblems.size(), cnt);
    }

    private void crawlAllTagParallelByTag(Tag tag) {
        getPages(tag).stream()
                .map(page -> getNumbers(tag, page))
                .flatMap(numbers -> solvedacWebClient.getSolvedAcProblemDtosByProblemNumbers(numbers).stream())
                .forEach(this::extractTagFromDto);
    }

    private void extractTagFromDto(SolvedAcProblemDto problemDto) {
        if (problemDto.getTags().isEmpty())
            return;
        if (baekjoonProblemRepository.existsByProblemNumber(problemDto.getProblemId())){
            Integer problemNumber = problemDto.getProblemId();
            log.info("[skipProblem]{}번 문제는 이미 DB에 존재하므로 skip됩니다", problemNumber);
            return;
        }

        Set<Tag> tags = problemDto.getTags().stream()
                .map(SolvedAcTagDto::getBojTagId)
                .map(Tag::of)
                .collect(Collectors.toSet());

        final Map<TagGroup, Tag> assignableGroups = tagGroupConst.assignableGroups(tags);

        BaekjoonProblem baekjoonProblem = BaekjoonProblem.builder()
                .tags(tags)
                .level(problemDto.getLevel())
                .problemNumber(problemDto.getProblemId())
                .build();
        BaekjoonProblem savedBaekjoonProblem = baekjoonProblemRepository.save(baekjoonProblem);

        if (problemDto.getTitles().stream().map(SolvedAcTitleDto::getLanguage).noneMatch(e -> e.equals("ko")))
            return;
        if (assignableGroups.isEmpty())
            return;

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
            log.info("[deleteComplete]{}번 포함 퀴즈 삭제 완료", tmpProblemNumber);
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
            log.info("[deleteProblemReserved]{}번 문제를 포함한 퀴즈들이 삭제될 예정입니다", baekjoonProblem.getProblemNumber());
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
