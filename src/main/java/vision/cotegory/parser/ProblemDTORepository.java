package vision.cotegory.parser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemDTORepository extends JpaRepository<ProblemDTO, Long> {
    Optional<ProblemDTO> findByProblemNumber(Integer problemNumber);
}
