package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
