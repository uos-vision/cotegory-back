package vision.cotegory.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
class SubmissionRepositoryTest {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Test
    @Transactional
    void correctCount() {
        final Long cnt = submissionRepository.streamFetchQuiz()
                .filter(submission -> submission.getSelectTag().equals(submission.getQuiz().getAnswerTag()))
                .count();
        log.info("{}", cnt);
    }
}