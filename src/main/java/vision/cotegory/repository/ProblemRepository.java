package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.problem.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
