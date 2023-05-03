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
import vision.cotegory.controller.request.CreateQuizRequest;
import vision.cotegory.controller.request.DeactivateRequest;
import vision.cotegory.controller.response.AbnormalQuizResponse;
import vision.cotegory.entity.AbnormalQuiz;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.TagGroup;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.exception.exception.NotSupportedOriginException;
import vision.cotegory.repository.AbnormalQuizRepository;
import vision.cotegory.repository.TagGroupRepository;
import vision.cotegory.service.StatisticService;
import vision.cotegory.service.QuizService;
import vision.cotegory.service.dto.CreateQuizDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Transactional
@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
public class AdminRestController {

    private final StatisticService statisticService;
    private final QuizService quizService;
    private final AbnormalQuizRepository abnormalQuizRepository;
    private final TagGroupRepository tagGroupRepository;

    @Operation(description = "모든문제(n)에 대한 모든제출(m)을 검사하므로 O(nm)입니다. 자주 호출하지 마세요.")
    @PostMapping("/abnormal/update")
    public void updateAbnormal() {
        statisticService.updateStatisticData();
    }

    @Operation(description = "update api호출로 생성된 데이터들을 봅니다. 자주 호출해도 됩니다.")
    @PostMapping("/abnormal/list")
    public Page<AbnormalQuizResponse> listAbnormal(Pageable pageable) {
        return abnormalQuizRepository.findAllFetchQuiz(pageable).map(AbnormalQuizResponse::new);
    }

    @PostMapping("/abnormal/deactivate")
    public void deactivateAbnormal(@RequestBody @Valid DeactivateRequest deactivateRequest) {
        AbnormalQuiz abnormalQuiz = abnormalQuizRepository.findById(deactivateRequest.getAbnormalQuizId())
                .orElseThrow(NotExistEntityException::new);

        abnormalQuiz.getQuiz().setActivated(false);
    }

    @PostMapping("/create-quiz")
    public void createQuiz(@RequestBody @Valid CreateQuizRequest createQuizRequest) {
        TagGroup tagGroup = tagGroupRepository.findById(createQuizRequest.getTagGroupId())
                .orElseThrow(NotExistEntityException::new);

        CreateQuizDto createQuizDto = CreateQuizDto.builder()
                .problemNumber(createQuizRequest.getProblemNumber())
                .title(createQuizRequest.getTitle())
                .tags(createQuizRequest.getTags())
                .timeLimit(createQuizRequest.getTimeLimit())
                .memoryLimit(createQuizRequest.getMemoryLimit())
                .problemBody(createQuizRequest.getProblemBody())
                .problemInput(createQuizRequest.getSampleInput())
                .problemOutput(createQuizRequest.getProblemOutput())
                .sampleInput(createQuizRequest.getSampleInput())
                .sampleOutput(createQuizRequest.getSampleOutput())
                .answerTag(createQuizRequest.getAnswerTag())
                .tagGroup(tagGroup)
                .build();

        if (createQuizRequest.getOrigin().equals(Origin.PROGRAMMERS))
            quizService.createProgrammersQuiz(createQuizDto);
        else if(createQuizRequest.getOrigin().equals(Origin.HANDWRITE))
            quizService.createHandWriteQuiz(createQuizDto);
        else
            throw new NotSupportedOriginException();
    }
}
