package vision.cotegory.crawler.baekjoon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import vision.cotegory.crawler.baekjoon.dto.BaekjoonProblemMetaDto;
import vision.cotegory.crawler.baekjoon.dto.SolvedAcProblemDto;
import vision.cotegory.crawler.baekjoon.dto.SolvedAcProblemDto.SolvedAcTagDto;
import vision.cotegory.entity.*;
import vision.cotegory.entity.problem.Problem;
import vision.cotegory.entity.problem.ProblemMeta;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.entity.tag.TagGroup;
import vision.cotegory.entity.tag.TagGroupConst;
import vision.cotegory.exception.exception.NotExistBaekjoonHandleException;
import vision.cotegory.repository.ProblemMetaRepository;
import vision.cotegory.repository.ProblemRepository;
import vision.cotegory.repository.QuizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
public class BaekjoonCrawler {

    private final ProblemRepository problemRepository;
    private final ProblemMetaRepository problemMetaRepository;
    private final SolvedAcWebClient solvedAcWebClient;
    private final TagGroupConst tagGroupConst;
    private final QuizRepository quizRepository;

    public void crawlAll() {
        var stopWatch = new StopWatch();
        stopWatch.start();
        Stream.of(Tag.values()).forEach(tag -> {
            if (tag.equals(Tag.OTHERS))
                return;
            crawlByTag(tag);
        });
        stopWatch.stop();
        log.info("[problemStopWatch]총 {}초 소요되었습니다", stopWatch.getTotalTimeSeconds());
        log.info("[problemMeta]총 {}개의 problemMeta가 저장되었습니다", problemMetaRepository.count());
        log.info("[problem]총 {}개의 problem이 저장되었습니다", problemRepository.count());
    }

    public void crawlByTag(Tag tag) {
        List<Integer> problemPageNumbers = new BaekjoonPageListCrawler(tag.toBaekjoonCode()).getProblemPageNumbers();

        problemPageNumbers.stream().map(problemPageNumber -> {
            BaekjoonPageListCrawler baekjoonPageListCrawler = new BaekjoonPageListCrawler(tag.toBaekjoonCode(), problemPageNumber);
            return baekjoonPageListCrawler.getProblemMetas();
        }).flatMap(dtos -> {
            List<BaekjoonProblemMetaDto> savedProblemMetaDtos = saveProblemMetas(dtos);

            List<Integer> problemNumbers = savedProblemMetaDtos.stream()
                    .map(BaekjoonProblemMetaDto::getProblemNumber)
                    .collect(Collectors.toList());
            return solvedAcWebClient.getSolvedAcProblemDtosByProblemNumbers(problemNumbers).stream();
        }).parallel().forEach(solvedAcProblemDto -> {
            if (isNotProperProblemToQuiz(solvedAcProblemDto)) {
                return;
            }

            Set<Tag> tags = solvedAcProblemDto.getTags().stream()
                    .map(SolvedAcTagDto::getBojTagId)
                    .map(Tag::of)
                    .collect(Collectors.toSet());
            Map<TagGroup, Tag> assignableGroups = tagGroupConst.assignableGroups(tags);
            if (assignableGroups.isEmpty())
                return;

            BaekjoonPageCrawler baekjoonPageCrawler = new BaekjoonPageCrawler(solvedAcProblemDto.getProblemId());

            if (baekjoonPageCrawler.getTimeLimit() == 0)
                return;
            ProblemMeta problemMeta = problemMetaRepository
                    .findByProblemNumberAndOrigin(solvedAcProblemDto.getProblemId(), Origin.BAEKJOON)
                    .orElseThrow(NotExistBaekjoonHandleException::new);

            Problem problem = Problem.builder()
                    .problemMeta(problemMeta)
                    .tags(tags)
                    .problemBody(baekjoonPageCrawler.getProblemBody())
                    .problemInput(baekjoonPageCrawler.getProblemInput())
                    .problemOutput(baekjoonPageCrawler.getProblemOutput())
                    .sampleInput(baekjoonPageCrawler.getSampleInput())
                    .sampleOutput(baekjoonPageCrawler.getSampleOutput())
                    .timeLimit(baekjoonPageCrawler.getTimeLimit())
                    .memoryLimit(baekjoonPageCrawler.getMemoryLimit())
                    .build();
            problemRepository.save(problem);

            log.info("[saveProblem]{}번({}):{} | {}",
                    problemMeta.getProblemNumber(),
                    problemMeta.getTitle(),
                    problem.getTags(),
                    assignableGroups);

            assignableGroups.forEach((tagGroup, answerTag) -> {
                Quiz quiz = Quiz.builder()
                        .problem(problem)
                        .tagGroup(tagGroup)
                        .answerTag(answerTag)
                        .activated(true)
                        .build();
                quizRepository.save(quiz);
                log.info("[createQuiz]{}번({}}: answerTag={} | {}",
                        problemMeta.getProblemNumber(),
                        problemMeta.getTitle(),
                        answerTag,
                        tagGroup);
            });
        });
    }

    private boolean isNotProperProblemToQuiz(SolvedAcProblemDto solvedAcProblemDto) {
        if (solvedAcProblemDto.getTitles().stream().noneMatch(e -> e.getLanguage().equals("ko")))
            return true;
        if (solvedAcProblemDto.getTags().isEmpty())
            return true;
        return false;
    }

    private List<BaekjoonProblemMetaDto> saveProblemMetas(List<BaekjoonProblemMetaDto> dtos) {
        var savedProblemMetaDtos = new ArrayList<BaekjoonProblemMetaDto>();
        for (var dto : dtos) {
            if (problemMetaRepository.findByProblemNumberAndOrigin(dto.getProblemNumber(), Origin.BAEKJOON).isPresent()) {
                log.info("[skipProblemMeta]{}번 problemMeta은 이미 ProblemMetaRepo에 존재하므로 skip됩니다", dto.getProblemNumber());
                continue;
            }
            ProblemMeta problemMeta = ProblemMeta.builder()
                    .problemNumber(dto.getProblemNumber())
                    .title(dto.getTitle())
                    .origin(Origin.BAEKJOON)
                    .url(String.format("https://www.acmicpc.net/problem/%d", dto.getProblemNumber()))
                    .build();

            log.info("[saveMeta]{}번({})", dto.getProblemNumber(), dto.getTitle());
            problemMetaRepository.save(problemMeta);
            savedProblemMetaDtos.add(dto);
        }
        return savedProblemMetaDtos;
    }
}
