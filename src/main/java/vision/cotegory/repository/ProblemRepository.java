package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.problem.Problem;

import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query("select p from Problem p where p.problemMeta.problemNumber = :problemNumber and p.problemMeta.origin = :origin")
    Optional<Problem> findByProblemNumberAndOrigin(@Param("problemNumber") Integer problemNumber, @Param("origin") Origin origin);
}
