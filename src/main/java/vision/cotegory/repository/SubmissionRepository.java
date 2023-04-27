package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Submission;
import vision.cotegory.entity.Tag;

import java.util.stream.Stream;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    //try with resource 와 함께 사용해 반드시 stream close 해야함
    @Query(value = "select s from Submission s join fetch s.quiz")
    Stream<Submission> streamFetchQuiz();

    Long countAllBySelectTag(Tag tag);
}
