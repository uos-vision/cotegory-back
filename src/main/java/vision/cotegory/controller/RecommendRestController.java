package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vision.cotegory.controller.response.RecommendResponse;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Recommend;
import vision.cotegory.entity.problem.ProblemMeta;
import vision.cotegory.service.RecommendService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
@Transactional
public class RecommendRestController {
    private final RecommendService recommendService;

    @Operation(description = "오늘의 문제추천")
    @GetMapping("/today")
    public ResponseEntity<RecommendResponse> recommendTodayProblem(@RequestHeader(value = "Authorization") Member member) {
        Recommend recommend = recommendService.findTodayProblem(member);
        return ResponseEntity.ok(new RecommendResponse(recommend));
    }

    @Operation(description = "오늘의 문제추천 새로고침")
    @PostMapping("/today")
    public void updateTodayProblem(@RequestHeader(value = "Authorization") Member member) {
        recommendService.updateTodayProblem(member);
    }

    @Operation(description = "기업 문제추천")
    @GetMapping("/company")
    public ResponseEntity<RecommendResponse> recommendCompanyProblem(@RequestHeader(value = "Authorization") Member member) {
        Recommend recommend = recommendService.findCompanyProblem(member);
        return ResponseEntity.ok(new RecommendResponse(recommend));
    }

    @Operation(description = "기업 문제추천 새로고침")
    @PostMapping("/company")
    public void updateCompanyProblem(@RequestHeader(value = "Authorization") Member member) {
        recommendService.updateCompanyProblem(member);
    }

    @Operation(description = "ai 문제추천")
    @GetMapping("/ai")
    public ResponseEntity<RecommendResponse> recommendAIProblem(@RequestHeader(value = "Authorization") Member member) {
        Recommend recommend = recommendService.findAIProblem(member);
        return ResponseEntity.ok(new RecommendResponse(recommend));
    }

    @Operation(description = "ai 문제추천 새로고침")
    @PostMapping("/ai")
    public void updateAIProblem(@RequestHeader(value = "Authorization") Member member) {
        recommendService.updateAIRecommend(member);
    }

    @Operation(description = "모든 타입에 대한 문제추천")
    @GetMapping("/all-type")
    public ResponseEntity<RecommendResponses> recommendAllProblem(@RequestHeader(value = "Authorization") Member member) {
        Recommend recommendAi = recommendService.findAIProblem(member);
        Recommend recommendCompany = recommendService.findCompanyProblem(member);
        Recommend recommendToday = recommendService.findTodayProblem(member);

        List<RecommendResponse> recommendResponses = Stream.of(recommendAi, recommendCompany, recommendToday)
                .map(RecommendResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new RecommendResponses(recommendResponses));
    }
}
