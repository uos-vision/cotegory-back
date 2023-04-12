package vision.cotegory.parser.baekjoon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.parser.baekjoon.entity.BaekjoonTag;

import java.util.List;

public interface BaekjoonTagRepository extends JpaRepository<BaekjoonTag, Long> {
    @Transactional(readOnly = true)
    Boolean existsByProblemNumber(Integer problemNumber);

    @Transactional(readOnly = true)
    @Query("select tag.problemNumber from BaekjoonTag tag")
    List<Integer> getAllProblemNumbers();
}
