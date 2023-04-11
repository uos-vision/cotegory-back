package vision.cotegory.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vision.cotegory.entity.Tag;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
public class BaekjoonDAO {

    private final BaekjoonParser baekjoonParser;
    private final ProblemDTORepository problemDTORepository;

    public ProblemDTO getByProblemNumber(Integer problemNumber) {
        return problemDTORepository.findByProblemNumber(problemNumber)
                .orElseGet(() -> crawlByProblemNumber(problemNumber));
    }

    public ProblemDTO updateByProblemNumber(Integer problemNumber) {
        problemDTORepository.findByProblemNumber(problemNumber).ifPresent(problemDTORepository::delete);
        return crawlByProblemNumber(problemNumber);
    }

    public void crawlAllByTag(Tag tag) {
        problemDTORepository.deleteAll();

        final List<Integer> problemPages = baekjoonParser
                .notLogin()
                .problemList(tag.toBaekjoonCode(), 1).getProblemPages();

        problemPages.stream().flatMap(e ->
                        baekjoonParser.notLogin().problemList(tag.toBaekjoonCode(), e).getProblemNumbers().stream())
                .forEach(this::crawlByProblemNumber);
    }

    private ProblemDTO crawlByProblemNumber(Integer problemNumber) {
        final BaekjoonParser.OnLoginProblem onLogin = baekjoonParser
                .onLogin()
                .problem(problemNumber);

        final BaekjoonParser.NotLoginProblem notLogin = baekjoonParser
                .notLogin()
                .problem(problemNumber);

        ProblemDTO problemDTO = ProblemDTO.builder()
                .tags(onLogin.getTags())
                .problemNumber(notLogin.getProblemNumber())
                .title(notLogin.getTitle())
                .timeLimit(notLogin.getTimeLimit())
                .memoryLimit(notLogin.getMemoryLimit())
                .subMissionCount(notLogin.getSubmissionCount())
                .correctAnswerCount(notLogin.getCorrectAnswerCount())
                .correctUserCount(notLogin.getCorrectUserCount())
                .correctRate(notLogin.getCorrectRate())
                .problemInput(notLogin.getProblemInput())
                .problemOutput(notLogin.getProblemOutput())
                .problemBody(notLogin.getProblemBody())
                .sampleInput(notLogin.getSampleInput())
                .sampleOutput(notLogin.getSampleOutput())
                .build();

        log.info("[ProblemDTO Create] {} : {}", problemDTO.getProblemNumber(), problemDTO.getTitle());
        return problemDTORepository.save(problemDTO);
    }
}
