package vision.cotegory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Submission;
import vision.cotegory.entity.tag.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    //try with resource 와 함께 사용해 반드시 stream close 해야함
    @Query(value = "select s from Submission s where s.quiz = :quiz")
    Stream<Submission> streamByQuiz(@Param("quiz") Quiz quiz);

    @Query("select s from Submission s")
    Stream<Submission> stream();

    List<Submission> findAllByQuiz(Quiz quiz);

    List<Submission> findAllByMember(Member member);
    Page<Submission> findAllByMember(Member member, Pageable pageable);

    List<Submission> findAllByMemberAndSubmitTimeBetween(Member member, LocalDateTime fromTime, LocalDateTime toTime);

    Long countAllByQuizAndSelectTag(Quiz quiz, Tag tag);

    Long countAllByQuiz(Quiz quiz);

}
