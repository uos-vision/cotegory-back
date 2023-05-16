package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vision.cotegory.controller.request.CreateSubmissionRequest;
import vision.cotegory.controller.request.DateSubmissionRequest;
import vision.cotegory.controller.request.SubmissionPageInfoRequest;
import vision.cotegory.controller.response.SubmissionPageInfoResponse;
import vision.cotegory.controller.response.SubmissionResponse;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Submission;
import vision.cotegory.exception.exception.BusinessException;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.repository.QuizRepository;
import vision.cotegory.repository.SubmissionRepository;
import vision.cotegory.service.SubmissionService;
import vision.cotegory.service.dto.CreateSubmissionDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/submission")
@Transactional
public class SubmissionRestController {

    private final SubmissionRepository submissionRepository;
    private final QuizRepository quizRepository;
    private final SubmissionService submissionService;

    @Operation(description = "yyyy-MM-dd'T'HH:mm:ss 형식을 요구로 합니다.")
    @GetMapping("/time")
    public ResponseEntity<List<SubmissionResponse>> timeSubmission(@RequestHeader(value = "Authorization")Member member, @Valid DateSubmissionRequest dateSubmissionRequest)
    {
        List<Submission> submissionList = submissionRepository.findAllByMemberAndSubmitTimeBetween(member, dateSubmissionRequest.getFromTime(), dateSubmissionRequest.getToTime());
        List<SubmissionResponse> resultList = submissionList.stream()
                .map(s -> new SubmissionResponse(s))
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }

    @PostMapping
    public void createSubmission(@RequestHeader(value = "Authorization") Member member, @RequestBody @Valid CreateSubmissionRequest createSubmissionRequest)
    {
        CreateSubmissionDto createSubmissionDto = CreateSubmissionDto.builder()
                .member(member)
                        .quiz(quizRepository.findById(createSubmissionRequest.getQuizId()).orElseThrow(NotExistEntityException::new))
                                .selectTag(createSubmissionRequest.getSelectTag())
                                        .submitTime(createSubmissionRequest.getSubmitTime())
                                                .playTime(createSubmissionRequest.getPlayTime())
                                                        .build();
        submissionService.createSubmission(createSubmissionDto);
    }

    @Operation(description = "submitTime과 playTime을 기준으로 페이징 하시면 됩니다.")
    @GetMapping("/pages")
    public ResponseEntity<List<SubmissionResponse>> pageSubmission(@RequestHeader(value = "Authorization")Member member, Pageable pageable)
    {
        Page<Submission> submissionList = submissionRepository.findAllByMember(member, pageable);
        List<SubmissionResponse> resultList = submissionList.stream()
                .map(s -> new SubmissionResponse(s))
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultList);
    }

    @Operation(description = "페이지 조회 기능 입니다. \n pageNum는 조회하고자 하는 페이지의 숫자입니다. \n pageSize는 조회하고자 하는 페이징 방식의 크기 입니다")
    @GetMapping("/page")
    public ResponseEntity<SubmissionPageInfoResponse> pageInfo(@RequestHeader(value = "Authorization")Member member, @Valid SubmissionPageInfoRequest sr)
    {
        Integer cnt = submissionRepository.findAllByMember(member).size();
        Integer totalPages = cnt % sr.getSize() == 0 ? cnt / sr.getSize() : cnt / sr.getSize() + 1;
        if(sr.getPageNum() > totalPages)
            throw new BusinessException();
        SubmissionPageInfoResponse submissionPageInfoResponse = SubmissionPageInfoResponse.builder()
                .totalDataCnt(cnt)
                .totalPages(totalPages)
                .isLastPage(sr.getPageNum() == totalPages)
                .isFirstPage(sr.getPageNum() == 1)
                .requestPage(sr.getPageNum())
                .requestSize(sr.getSize())
                .build();
        return ResponseEntity.ok(submissionPageInfoResponse);
    }

}
