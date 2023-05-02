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

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
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
                ConcurrentHashMap<Tag, Long> tagCount = new ConcurrentHashMap<>();

                try (Stream<Submission> submissionStream = submissionRepository.streamByQuiz(quiz)) {
                    submissionStream.parallel().forEach(submission -> tagCount
                            .merge(submission.getSelectTag(), 1L, Long::sum));
                }

                Long submitCount = tagCount.values().stream().reduce(0L, Long::sum);
                Long correctCount = tagCount.get(quiz.getAnswerTag());
                if (submitCount.equals(0L))
                    return;

                Double correctRate = (double) correctCount / (double) submitCount;
                if (!isAbnormalQuiz(submitCount, correctRate))
                    return;

                AbnormalQuiz abnormalQuiz = AbnormalQuiz.builder()
                        .selectedTagCount(new HashMap<>(tagCount))
                        .quiz(quiz)
                        .submitCount(submitCount)
                        .correctRate(correctRate)
                        .build();

                abnormalQuizRepository.save(abnormalQuiz);
            });
        }
    }

    private boolean isAbnormalQuiz(Long submitCount, Double correctRate) {
        final Double correctRateThreshold = 0.3;
        final Long minimumSubmitCountThreshold = 5L;

        return submitCount.compareTo(minimumSubmitCountThreshold) > 0
                && correctRate.compareTo(correctRateThreshold) < 0;
    }
}
