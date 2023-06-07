package vision.cotegory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Submission;

import java.time.LocalDateTime;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findAllByMemberAndIsSkippedIsTrue(Member member);

    Page<Submission> findAllByMemberAndIsSkippedIsTrue(Member member, Pageable pageable);

    Page<Submission> findAllByMemberAndIsSkippedIsTrueAndSubmitTimeBetween(Member member, LocalDateTime fromTime, LocalDateTime toTime, Pageable pageable);
}
