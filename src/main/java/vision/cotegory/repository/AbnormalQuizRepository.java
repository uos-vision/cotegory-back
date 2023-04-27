package vision.cotegory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.AbnormalQuiz;

public interface AbnormalQuizRepository extends JpaRepository<AbnormalQuiz, Long> {
    Page<AbnormalQuiz> findAll(Pageable pageable);
}
