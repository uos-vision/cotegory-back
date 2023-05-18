package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vision.cotegory.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginIdAndActivatedIsTrue(String loginId);

    @Query("select m.id from Member m where m.activated = TRUE and m.loginId = :loginId")
    Optional<Long> getIdByLoginId(@Param("loginId") String loginId);

}
