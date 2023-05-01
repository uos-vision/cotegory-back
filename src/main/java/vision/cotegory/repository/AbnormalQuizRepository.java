package vision.cotegory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vision.cotegory.entity.AbnormalQuiz;

public interface AbnormalQuizRepository extends JpaRepository<AbnormalQuiz, Long> {
    Page<AbnormalQuiz> findAll(Pageable pageable);

    @Query(
            value = "select aq from AbnormalQuiz aq join fetch aq.quiz",
            countQuery = "select count(aq) from AbnormalQuiz aq"
    )
    Page<AbnormalQuiz> findAllFetchQuiz(Pageable pageable);


}
