package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vision.cotegory.controller.request.CreateProblemMetaRequest;
import vision.cotegory.controller.request.CreateQuizRequest;
import vision.cotegory.controller.request.DeactivateRequest;
import vision.cotegory.controller.response.AbnormalQuizResponse;
import vision.cotegory.problemloader.baekjoon.BaekjoonCrawler;
import vision.cotegory.entity.AbnormalQuiz;
import vision.cotegory.entity.tag.TagGroup;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.problemloader.programmers.ProgrammersCSVReader;
import vision.cotegory.repository.AbnormalQuizRepository;
import vision.cotegory.repository.TagGroupRepository;
import vision.cotegory.service.ProblemMetaService;
import vision.cotegory.service.QuizService;
import vision.cotegory.service.StatisticService;
import vision.cotegory.service.dto.CreateProblemMetaDto;
import vision.cotegory.service.dto.CreateQuizDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
public class AdminRestController {

    private final AbnormalQuizRepository abnormalQuizRepository;
    private final TagGroupRepository tagGroupRepository;

    private final StatisticService statisticService;
    private final QuizService quizService;
    private final ProblemMetaService problemMetaService;

    private final BaekjoonCrawler baekjoonCrawler;
    private final ProgrammersCSVReader programmersCSVReader;

    @Operation(description = "전체유저 정답률과 비정상 데이터 리스트를 업데이트 합니다")
    @PostMapping("/statistic/update")
    public void updateAbnormal() {
        statisticService.updateStatisticData();
    }

    @Transactional
    @Operation(description = "비정상 데이터 리스트를 봅니다.")
    @GetMapping("/abnormal/list")
    public Page<AbnormalQuizResponse> listAbnormal(@ParameterObject Pageable pageable) {
        return abnormalQuizRepository.findAllActivateTrue(pageable).map(AbnormalQuizResponse::new);
    }

    @Operation(description = "비정상 Quiz를 비활성화 합니다. QuizId가 아니라 AbnormalQuizId를 받습니다")
    @Transactional
    @PostMapping("/abnormal/deactivate")
    public void deactivateAbnormal(@RequestBody @Valid DeactivateRequest deactivateRequest) {
        AbnormalQuiz abnormalQuiz = abnormalQuizRepository.findById(deactivateRequest.getAbnormalQuizId())
                .orElseThrow(NotExistEntityException::new);

        abnormalQuiz.getQuiz().setActivated(false);
    }

    @Transactional
    @PostMapping("/create-quiz")
    public void createQuiz(@RequestBody @Valid CreateQuizRequest createQuizRequest) {
        TagGroup tagGroup = tagGroupRepository.findById(createQuizRequest.getTagGroupId())
                .orElseThrow(NotExistEntityException::new);

        CreateQuizDto createQuizDto = CreateQuizDto.builder()
                .answerTag(createQuizRequest.getAnswerTag())
                .tagGroup(tagGroup)
                .problemNumber(createQuizRequest.getProblemNumber())
                .origin(createQuizRequest.getOrigin())
                .title(createQuizRequest.getTitle())
                .url(createQuizRequest.getUrl())
                .tags(createQuizRequest.getTags())
                .problemBody(createQuizRequest.getProblemBody())
                .problemInput(createQuizRequest.getProblemInput())
                .problemOutput(createQuizRequest.getProblemOutput())
                .sampleInput(createQuizRequest.getSampleInput())
                .sampleOutput(createQuizRequest.getSampleOutput())
                .timeLimit(createQuizRequest.getTimeLimit())
                .memoryLimit(createQuizRequest.getMemoryLimit())
                .build();

        quizService.createQuiz(createQuizDto);
    }

    @Transactional
//    @PostMapping("/create-meta")
    public void createProblemMeta(@RequestBody @Valid CreateProblemMetaRequest createProblemMetaRequest) {
        CreateProblemMetaDto createProblemMetaDto = CreateProblemMetaDto.builder()
                .origin(createProblemMetaRequest.getOrigin())
                .url(createProblemMetaRequest.getUrl())
                .problemNumber(createProblemMetaRequest.getProblemNumber())
                .title(createProblemMetaRequest.getTitle())
                .isCompany(createProblemMetaRequest.getIsCompany())
                .build();
        problemMetaService.createProblemMeta(createProblemMetaDto);
    }

    @Operation(description = "백준문제와 프로그래머스 문제를 DB에 저장합니다")
    @PostMapping("/problem-load")
    public void updateBaekjoonProblems() {
        baekjoonCrawler.crawlAll();
        programmersCSVReader.readCSV();
    }
}
