package vision.cotegory.parser.baekjoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.parser.baekjoon.entity.BaekjoonProblem;

import java.util.List;
import java.util.Optional;

public interface BaekjoonProblemRepository extends JpaRepository<BaekjoonProblem, Long> {
}
