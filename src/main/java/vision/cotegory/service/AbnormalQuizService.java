package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vision.cotegory.entity.AbnormalQuiz;
import vision.cotegory.entity.Tag;
import vision.cotegory.repository.AbnormalQuizRepository;
import vision.cotegory.repository.QuizRepository;
import vision.cotegory.repository.SubmissionRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbnormalQuizService {

    private final AbnormalQuizRepository abnormalQuizRepository;
    private final QuizRepository quizRepository;
    private final SubmissionRepository submissionRepository;

    private final Double correctRateThreshold = 30.0;
    private final Long minimumSubmitCountThreshold = 5L;

    public void updateAbnormalQuizzes() {
        abnormalQuizRepository.deleteAll();

        quizRepository.findAllBy().forEach(quiz -> {
            Long submitCount = submissionRepository.count();
            Long correctCount = submissionRepository.correctCount();

            Double correctRate = Double.valueOf(correctCount) / Double.valueOf(submitCount);
            if (submitCount.compareTo(minimumSubmitCountThreshold) < 0
                    && correctRate.compareTo(correctRateThreshold) < 0) {

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
            }
        });
    }
}
