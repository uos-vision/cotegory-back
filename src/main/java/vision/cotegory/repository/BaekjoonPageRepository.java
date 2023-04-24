package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.problem.BaekjoonProblemPage;

public interface BaekjoonPageRepository extends JpaRepository<BaekjoonProblemPage, Long> {
}
