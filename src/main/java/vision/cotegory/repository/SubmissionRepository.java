package vision.cotegory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Submission;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    @Query("select s from Submission s")
    Stream<Submission> stream();

    List<Submission> findAllByMember(Member member);

    Page<Submission> findAllByMember(Member member, Pageable pageable);

    List<Submission> findAllByMemberAndSubmitTimeBetween(Member member, LocalDateTime fromTime, LocalDateTime toTime);
}
