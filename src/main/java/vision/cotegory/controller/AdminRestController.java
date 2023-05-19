package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vision.cotegory.controller.request.CreateProblemMetaRequest;
import vision.cotegory.controller.request.CreateQuizRequest;
import vision.cotegory.controller.request.DeactivateRequest;
import vision.cotegory.controller.response.AbnormalQuizResponse;
import vision.cotegory.crawler.baekjoon.BaekjoonCrawler;
import vision.cotegory.entity.AbnormalQuiz;
import vision.cotegory.entity.tag.TagGroup;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.repository.AbnormalQuizRepository;
import vision.cotegory.repository.TagGroupRepository;
import vision.cotegory.service.ProblemService;
import vision.cotegory.service.StatisticService;
import vision.cotegory.service.QuizService;
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

    private final BaekjoonCrawler baekjoonCrawler;

    private final StatisticService statisticService;
    private final QuizService quizService;
    private final ProblemService problemService;

    @Operation(description = "모든문제(n)에 대한 모든제출(m)을 검사하므로 O(nm)입니다. 자주 호출하지 마세요.\\\n전체유저 정답률과 비정상 데이터 리스트를 업데이트 합니다")
    @PostMapping("/statistic/update")
    public void updateAbnormal() {
        statisticService.updateStatisticData();
    }

    @Transactional
    @Operation(description = "update api호출로 생성된 데이터들을 봅니다. 자주 호출해도 됩니다.")
    @PostMapping("/abnormal/list")
    public Page<AbnormalQuizResponse> listAbnormal(Pageable pageable) {
        return abnormalQuizRepository.findAllActivateTrue(pageable).map(AbnormalQuizResponse::new);
    }

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
                .url(createQuizRequest.getUrl())
                .problemNumber(createQuizRequest.getProblemNumber())
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
    @PostMapping("/create-meta")
    public void createProblemMeta(@RequestBody @Valid CreateProblemMetaRequest createProblemMetaRequest) {
        CreateProblemMetaDto createProblemMetaDto = CreateProblemMetaDto.builder()
                .origin(createProblemMetaRequest.getOrigin())
                .url(createProblemMetaRequest.getUrl())
                .problemNumber(createProblemMetaRequest.getProblemNumber())
                .title(createProblemMetaRequest.getTitle())
                .build();
        problemService.createProblemMeta(createProblemMetaDto);
    }

    @Operation(description = "프론트에서 호출할일은 거의 없습니다", summary = "5분이상 걸리는 작업입니다")
    @PostMapping("/baekjoon/crawl")
    public void updateBaekjoonProblems() {
        baekjoonCrawler.crawlAll();
    }
}
