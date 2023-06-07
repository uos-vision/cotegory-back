package vision.cotegory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Submission;

import java.time.LocalDateTime;
import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findAllByMember(Member member);

    Page<Submission> findAllByMemberAndIsSkippedIsFalse(Member member, Pageable pageable);

    Page<Submission> findAllByMemberAndIsSkippedIsFalseAndSubmitTimeBetween(Member member, LocalDateTime fromTime, LocalDateTime toTime, Pageable pageable);
}
