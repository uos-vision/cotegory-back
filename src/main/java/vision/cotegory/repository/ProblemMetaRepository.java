package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.problem.ProblemMeta;

import java.util.List;
import java.util.Optional;

public interface ProblemMetaRepository extends JpaRepository<ProblemMeta, Long> {
    @Query("select pm from ProblemMeta pm where pm.problemNumber = :problemNumber and pm.origin = :origin")
    Optional<ProblemMeta> findByProblemNumberAndOrigin(@Param("problemNumber") Integer problemNumber, @Param("origin") Origin origin);

    List<ProblemMeta> findAllByIsCompanyIsTrue();
}
