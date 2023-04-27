package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vision.cotegory.entity.Submission;
import vision.cotegory.entity.Tag;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    @Query("select count(s) from Submission s join fetch s.quiz q where s.selectTag = q.answerTag")
    Long correctCount();

    Long countAllBySelectTag(Tag tag);
}
