package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.AbnormalQuiz;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Submission;
import vision.cotegory.entity.Tag;
import vision.cotegory.repository.AbnormalQuizRepository;
import vision.cotegory.repository.QuizRepository;
import vision.cotegory.repository.SubmissionRepository;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class AbnormalQuizService {

    private final AbnormalQuizRepository abnormalQuizRepository;
    private final QuizRepository quizRepository;
    private final SubmissionRepository submissionRepository;

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

                Double correctRate = Double.valueOf(correctCount) / Double.valueOf(submitCount);
                if (!isAbnormalQuiz(submitCount, correctRate))
                    return;

                Map<Tag, Long> selectedTagCount = quiz.getTagGroup()
                        .getTags()
                        .stream()
                        .collect(Collectors.toMap(tag -> tag, submissionRepository::countAllBySelectTag));

                AbnormalQuiz abnormalQuiz = AbnormalQuiz.builder()
                        .selectedTagCount(selectedTagCount)
                        .quiz(quiz)
                        .submitCount(submitCount)
                        .correctRate(correctRate)
                        .build();

                abnormalQuizRepository.save(abnormalQuiz);
            });
        }
    }

    private boolean isAbnormalQuiz(Long submitCount, Double correctRate) {
        final Double correctRateThreshold = 30.0;
        final Long minimumSubmitCountThreshold = 5L;

        return submitCount.compareTo(minimumSubmitCountThreshold) < 0
                && correctRate.compareTo(correctRateThreshold) < 0;
    }
}
