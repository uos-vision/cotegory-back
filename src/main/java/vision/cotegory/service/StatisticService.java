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
import vision.cotegory.utils.ElOUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class StatisticService {

    private final AbnormalQuizRepository abnormalQuizRepository;
    private final QuizRepository quizRepository;
    private final SubmissionRepository submissionRepository;
    private final ElOUtils elOUtils;

    public void updateStatisticData() {
        abnormalQuizRepository.deleteAll();

        List<Quiz> quizzes = quizRepository.findAll();

        long totalSubmissionCount = 0L;
        long totalCorrectCount = 0L;

        for (var quiz : quizzes) {
            Map<Tag, Long> tagCount = new HashMap<>();
            List<Submission> submissions = submissionRepository.findAllByQuiz(quiz);
            for (var submission : submissions)
                tagCount.merge(submission.getSelectTag(), 1L, Long::sum);

            long submissionCount = tagCount.values().stream().reduce(0L, Long::sum);
            long correctCount = tagCount.get(quiz.getAnswerTag());

            totalSubmissionCount += submissionCount;
            totalCorrectCount += correctCount;

            if(submissionCount == 0L)
                continue;
            double correctRate = (double) correctCount / (double) submissionCount;
            if(!isAbnormalQuiz(submissionCount, correctRate))
                continue;
            AbnormalQuiz abnormalQuiz = AbnormalQuiz.builder()
                    .selectedTagCount(tagCount)
                    .correctRate(correctRate)
                    .submitCount(submissionCount)
                    .quiz(quiz)
                    .build();

            abnormalQuizRepository.save(abnormalQuiz);
        }
        if(totalSubmissionCount != 0L)
        {
            double totalCorrectRate = (double) totalCorrectCount / (double) totalSubmissionCount;
            elOUtils.updateCorrectRate(totalCorrectRate);
        }
    }

    private boolean isAbnormalQuiz(Long submitCount, Double correctRate) {
        final Double correctRateThreshold = 0.3;
        final Long minimumSubmitCountThreshold = 5L;

        return submitCount.compareTo(minimumSubmitCountThreshold) > 0
                && correctRate.compareTo(correctRateThreshold) < 0;
    }
}
