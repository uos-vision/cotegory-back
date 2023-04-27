package vision.cotegory.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.AbnormalQuiz;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Submission;
import vision.cotegory.entity.Tag;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
class SubmissionRepositoryTest {

    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private AbnormalQuizRepository abnormalQuizRepository;

    @Test
    @Transactional
    public void updateAbnormalQuizzes() {
        abnormalQuizRepository.deleteAll();

        try (Stream<Quiz> quizStream = quizRepository.stream()) {
            quizStream.forEach(quiz -> {
                Long submitCount = submissionRepository.count();
                Long correctCount;
                try (Stream<Submission> submissionStream = submissionRepository.streamByQuiz(quiz)) {
                    correctCount = submissionStream
                            .filter(submission -> submission.getSelectTag().equals(quiz.getAnswerTag()))
                            .count();
                }
            });
        }
    }
}