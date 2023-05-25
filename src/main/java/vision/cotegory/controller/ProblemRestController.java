package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vision.cotegory.controller.response.ProblemMetaResponse;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.problem.Problem;
import vision.cotegory.entity.problem.ProblemMeta;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.service.ProblemMetaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problem")
@Transactional
public class ProblemRestController {
    private final ProblemMetaService problemMetaService;

//    @GetMapping("/baekjoon/{problemNum}")
    public ResponseEntity<ProblemMetaResponse> findBaekjoonProblem(@PathVariable("problemNum") Integer problemNum) {
        Problem baekjoonProblem = problemMetaService
                .findBaekjoonProblem(problemNum)
                .orElseThrow(NotExistEntityException::new);
        return ResponseEntity.ok(new ProblemMetaResponse(baekjoonProblem.getProblemMeta()));
    }

    @Operation(summary = "랜덤한 기업문제를 보여줍니다.")
//    @GetMapping("/company")
    public ResponseEntity<ProblemMetaResponse> findCompanyProblem() {
        ProblemMeta companyProblemMeta = problemMetaService.findCompanyProblem();

        return ResponseEntity.ok(new ProblemMetaResponse(companyProblemMeta));
    }

    //비회원을 위한 기능이 있을지도 몰라서 일단 만들었습니다.
//    @GetMapping("/today")
    public ResponseEntity<ProblemMetaResponse> findTodayProblem() {
        Problem problem = problemMetaService.findTodayProblem();
        ProblemMetaResponse problemResponse = new ProblemMetaResponse(problem.getProblemMeta());
        return ResponseEntity.ok(problemResponse);
    }
}
