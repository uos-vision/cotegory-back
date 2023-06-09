package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vision.cotegory.controller.request.CreateSkipRequest;
import vision.cotegory.controller.request.CreateSubmissionRequest;
import vision.cotegory.controller.request.DateSubmissionRequest;
import vision.cotegory.controller.response.CreateSubmissionResponse;
import vision.cotegory.controller.response.SubmissionResponse;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Submission;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.repository.SubmissionRepository;
import vision.cotegory.service.QuizService;
import vision.cotegory.service.SubmissionService;
import vision.cotegory.service.dto.CreateSkippedSubmissionDto;
import vision.cotegory.service.dto.CreateSubmissionDto;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/submission")
@Transactional
public class SubmissionRestController {
    private final SubmissionService submissionService;
    private final QuizService quizService;
    private final SubmissionRepository submissionRepository;

    @Operation(description = "Quiz에 대한 답을 제출합니다")
    @PostMapping
    public ResponseEntity<CreateSubmissionResponse> createSubmission(@RequestHeader(value = "Authorization") Member member, @RequestBody @Valid CreateSubmissionRequest createSubmissionRequest) {
        CreateSubmissionDto createSubmissionDto = CreateSubmissionDto.builder()
                .member(member)
                .quiz(quizService.findQuiz(createSubmissionRequest.getQuizId()).orElseThrow(NotExistEntityException::new))
                .selectTag(createSubmissionRequest.getSelectTag())
                .submitTime(LocalDateTime.now())
                .playTime(createSubmissionRequest.getPlayTime())
                .build();

        Submission submission = submissionService.createSubmission(createSubmissionDto);
        return ResponseEntity.ok(new CreateSubmissionResponse(submission));
    }

    @Operation(description = "Quiz에 대한 답을 제출합니다")
    @PostMapping("/skip")
    public void createSkippedSubmission(@RequestHeader(value = "Authorization") Member member, @RequestBody @Valid CreateSkipRequest createSkipRequest) {
        CreateSkippedSubmissionDto createSkippedSubmissionDto = CreateSkippedSubmissionDto
                .builder()
                .member(member)
                .quiz(quizService.findQuiz(createSkipRequest.getQuizId()).orElseThrow(NotExistEntityException::new))
                .build();
        submissionService.createSkippedSubmission(createSkippedSubmissionDto);
    }

    @Operation(description = "yyyy-MM-dd'T'HH:mm:ss 형식을 요구로 합니다.")
    @GetMapping("/time")
    public Page<SubmissionResponse> timeSubmission(
            @RequestHeader(value = "Authorization") Member member,
            @ParameterObject @ModelAttribute @Valid DateSubmissionRequest dateSubmissionRequest,
            @ParameterObject @PageableDefault(sort = "submitTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return submissionService.findAllByTime(member, dateSubmissionRequest.getFromTime(), dateSubmissionRequest.getToTime(), pageable)
                .map(SubmissionResponse::new);
    }

    @Operation(description = "현재 로그인한 유저의 모든 Submission을 봅니다.\\\n기본값으로, 최근제출순으로 정렬되어있습니다")
    @GetMapping("/list")
    public Page<SubmissionResponse> pageSubmission(
            @RequestHeader(value = "Authorization") Member member,
            @ParameterObject @PageableDefault(sort = "submitTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return submissionRepository.findAllByMemberAndIsSkippedIsFalse(member, pageable)
                .map(SubmissionResponse::new);
    }
}
