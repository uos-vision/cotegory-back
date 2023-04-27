package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Tag;
import vision.cotegory.entity.TagGroup;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Long countAllByTagGroupAndAnswerTag(TagGroup tagGroup, Tag answerTag);

    Stream<Quiz> findAllBy();
}
