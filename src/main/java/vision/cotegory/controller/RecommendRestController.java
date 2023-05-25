package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vision.cotegory.controller.response.RecommendResponse;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.problem.ProblemMeta;
import vision.cotegory.service.RecommendService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
@Transactional
public class RecommendRestController {
    private final RecommendService recommendService;

    @Operation(description = "오늘의 문제추천")
    @GetMapping("/today")
    public ResponseEntity<RecommendResponse> recommendTodayProblem(@RequestHeader(value = "Authorization") Member member) {
        ProblemMeta problemMeta = recommendService.findTodayProblem(member);
        return ResponseEntity.ok(new RecommendResponse(problemMeta));
    }

    @Operation(description = "오늘의 문제추천 새로고침")
    @PostMapping("/today")
    public void updateTodayProblem(@RequestHeader(value = "Authorization") Member member) {
        recommendService.updateTodayProblem(member);
    }

    @Operation(description = "기업 문제추천")
    @GetMapping("/company")
    public ResponseEntity<RecommendResponse> recommendCompanyProblem(@RequestHeader(value = "Authorization") Member member) {
        ProblemMeta problemMeta = recommendService.findCompanyProblem(member);
        return ResponseEntity.ok(new RecommendResponse(problemMeta));
    }

    @Operation(description = "기업 문제추천 새로고침")
    @PostMapping("/company")
    public void updateCompanyProblem(@RequestHeader(value = "Authorization") Member member) {
        recommendService.updateCompanyProblem(member);
    }

    @Operation(description = "ai 문제추천")
    @GetMapping("/ai")
    public ResponseEntity<RecommendResponse> recommendAIProblem(@RequestHeader(value = "Authorization") Member member) {
        ProblemMeta problemMeta = recommendService.findAIProblem(member);
        return ResponseEntity.ok(new RecommendResponse(problemMeta));
    }

    @Operation(description = "ai 문제추천 새로고침")
    @PostMapping("/ai")
    public void updateAIProblem(@RequestHeader(value = "Authorization") Member member) {
        recommendService.updateAIRecommend(member);
    }

}
