package vision.cotegory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vision.cotegory.controller.response.QuizResponse;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.repository.QuizRepository;
import vision.cotegory.repository.SubmissionRepository;
import vision.cotegory.service.QuizService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
@Transactional
public class QuizRestController {
    private final QuizService quizService;

    @GetMapping("/{id}")
    public ResponseEntity<QuizResponse> quiz(@PathVariable("id") Long id) {
        Quiz quiz = quizService.findQuiz(id).orElseThrow(NotExistEntityException::new);
        QuizResponse quizResponse = new QuizResponse(quiz);
        return ResponseEntity.ok(quizResponse);
    }

    @GetMapping("/recommend")
    public ResponseEntity<QuizResponse> recommendQuiz(@RequestHeader(value = "Authorization") Member member) {
        Quiz quiz = quizService.recommendQuiz(member);
        QuizResponse quizResponse = new QuizResponse(quiz);
        return ResponseEntity.ok(quizResponse);
    }
}
