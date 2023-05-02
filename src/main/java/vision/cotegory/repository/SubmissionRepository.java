package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Submission;

import java.util.stream.Stream;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    //try with resource 와 함께 사용해 반드시 stream close 해야함
    @Transactional(readOnly = true)
    @Query(value = "select s from Submission s where s.quiz = :quiz")
    Stream<Submission> streamByQuiz(@Param("quiz") Quiz quiz);

    Long countAllByQuiz(Quiz quiz);
}
