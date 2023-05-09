package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.problem.BaekjoonProblem;

import java.util.List;
import java.util.Optional;

public interface BaekjoonProblemRepository extends JpaRepository<BaekjoonProblem, Long> {
    @Transactional(readOnly = true)
    Boolean existsByProblemNumber(Integer problemNumber);

    @Transactional(readOnly = true)
    @Query("select tag.problemNumber from BaekjoonProblem tag")
    List<Integer> getAllProblemNumbers();

    Optional<BaekjoonProblem> findByProblemNumber(Integer problemNumber);
}
