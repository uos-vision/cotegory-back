package vision.cotegory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vision.cotegory.controller.request.CreateCompanyProblemRequest;
import vision.cotegory.controller.response.ProblemResponse;
import vision.cotegory.entity.problem.BaekjoonProblem;
import vision.cotegory.entity.problem.Problem;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.service.ProblemService;
import vision.cotegory.service.dto.CreateCompanyProblemDto;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/company")
    public ResponseEntity<ProblemResponse> findCompanyProblem() {
        List<String> list = problemService.findCompanyProblem();
        ProblemResponse problemResponse = new ProblemResponse(list.get(0), Integer.parseInt(list.get(1)), "https://school.programmers.co.kr/learn/courses/30/lessons/" + list.get(1));
        return ResponseEntity.ok(problemResponse);
    }

    @PostMapping("/company")
    public void createCompanyProblem(@RequestBody @Valid CreateCompanyProblemRequest createCompanyProblemRequest) {
        CreateCompanyProblemDto createCompanyProblemDto = CreateCompanyProblemDto
                .builder()
                .problemName(createCompanyProblemRequest.getProblemName())
                .problemNum(createCompanyProblemRequest.getProblemNum())
                .origin(createCompanyProblemRequest.getOrigin())
                .build();
        problemService.createCompanyProblem(createCompanyProblemDto);
    } //AdminRestController로 옮길예정

    @GetMapping("/today")
    public ResponseEntity<ProblemResponse> findTodayProblem() {
        Problem problem = problemService.findTodayProblem();
        ProblemResponse problemResponse = new ProblemResponse(problem);
        return ResponseEntity.ok(problemResponse);
    }

}
