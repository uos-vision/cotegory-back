package vision.cotegory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vision.cotegory.controller.response.ProblemResponse;
import vision.cotegory.entity.problem.BaekjoonProblem;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.service.ProblemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problem")
@Transactional
public class ProblemRestController {
    private final ProblemService problemService;

    @GetMapping("/backjoon/{problemNum}")
    public ResponseEntity<ProblemResponse> findBaekjoonProblem(@PathVariable("problemNum") Integer problemNum) {
        BaekjoonProblem baekjoonProblem = problemService.findBaekjoonProblem(problemNum).orElseThrow(NotExistEntityException::new);
        ProblemResponse problemResponse = new ProblemResponse(baekjoonProblem);
        return ResponseEntity.ok(problemResponse);
    }
}
