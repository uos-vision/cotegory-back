package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.info.BaekjoonProblemInfo;

import java.util.List;

public interface BaekjoonTagRepository extends JpaRepository<BaekjoonProblemInfo, Long> {
    @Transactional(readOnly = true)
    Boolean existsByProblemNumber(Integer problemNumber);

    @Transactional(readOnly = true)
    @Query("select tag.problemNumber from BaekjoonProblemInfo tag")
    List<Integer> getAllProblemNumbers();
}
