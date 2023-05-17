package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.Recommend;

import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    // Optional<Recommend> findTopByMemberAndOriginOrderByCreateTimeDesc(Member member, Origin origin);
}
