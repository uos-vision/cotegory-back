package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.info.BaekjoonProblemPage;

public interface BaekjoonPageRepository extends JpaRepository<BaekjoonProblemPage, Long> {
}
