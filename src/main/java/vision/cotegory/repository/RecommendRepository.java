package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.Recommend;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
}
