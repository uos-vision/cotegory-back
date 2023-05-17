package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.AbnormalQuiz;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Submission;
import vision.cotegory.repository.AbnormalQuizRepository;
import vision.cotegory.repository.QuizRepository;
import vision.cotegory.repository.SubmissionRepository;
import vision.cotegory.utils.ElOUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StatisticService {

    private final AbnormalQuizRepository abnormalQuizRepository;
    private final QuizRepository quizRepository;
    private final SubmissionRepository submissionRepository;
    private final ElOUtils eloUtils;

    public void updateStatisticData() {
        abnormalQuizRepository.deleteAll();

        List<Quiz> quizzes = quizRepository.findAll();

        long totalSubmissionCount = 0L;
        long totalCorrectCount = 0L;

        for (var quiz : quizzes) {

            long submissionCount = quiz.getSubmitCount();
            long correctCount = quiz.getCorrectCount();

            if(submissionCount == 0L)
                continue;

            totalSubmissionCount += submissionCount;
            totalCorrectCount += correctCount;

            if(!isAbnormalQuiz(submissionCount, quiz.getCorrectRate()))
                continue;

            AbnormalQuiz abnormalQuiz = AbnormalQuiz.builder().quiz(quiz).build();
            abnormalQuizRepository.save(abnormalQuiz);
        }

        if(totalSubmissionCount != 0L)
        {
            double totalCorrectRate = (double) totalCorrectCount / (double) totalSubmissionCount;
            eloUtils.updateCorrectRate(totalCorrectRate);
        }
    }

    private boolean isAbnormalQuiz(Long submitCount, Double correctRate) {
        final Double correctRateThreshold = 0.3;
        final Long minimumSubmitCountThreshold = 5L;

        return submitCount.compareTo(minimumSubmitCountThreshold) > 0
                && correctRate.compareTo(correctRateThreshold) < 0;
    }
}
