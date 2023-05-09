package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.problem.BaekjoonProblemPage;

import java.util.Optional;

public interface BaekjoonPageRepository extends JpaRepository<BaekjoonProblemPage, Integer> {
    Optional<BaekjoonProblemPage> findByProblemNumber(Integer problemNumber);
}
