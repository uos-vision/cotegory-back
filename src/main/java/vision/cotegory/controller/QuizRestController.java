package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vision.cotegory.controller.response.OriginListResponse;
import vision.cotegory.controller.response.QuizResponse;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.Quiz;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.service.QuizService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
@Transactional
public class QuizRestController {
    private final QuizService quizService;

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> quiz(@PathVariable("id") Long id) {
        Quiz quiz = quizService.findQuiz(id).orElseThrow(NotExistEntityException::new);
        return ResponseEntity.ok(new QuizResponse(quiz));
    }

    @Operation(description = "퀴즈 출제를 요청합니다")
    @GetMapping
    public ResponseEntity<QuizResponse> recommendQuiz(@RequestHeader(value = "Authorization") Member member) {
        Quiz quiz = quizService.recommendQuiz(member);
        return ResponseEntity.ok(new QuizResponse(quiz));
    }

    @Operation(description = "지원하는 사이트 목록을 봅니다")
    @GetMapping("/origin/list")
    public ResponseEntity<OriginListResponse> originList() {
        return ResponseEntity.ok(new OriginListResponse(List.of(Origin.values())));
    }
}
