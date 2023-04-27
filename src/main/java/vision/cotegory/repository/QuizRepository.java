package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Quiz;
import vision.cotegory.entity.Tag;
import vision.cotegory.entity.TagGroup;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Long countAllByTagGroupAndAnswerTag(TagGroup tagGroup, Tag answerTag);

    //try with resource 와 함께 사용해 반드시 stream close 해야함
    @Query("select q from Quiz q")
    Stream<Quiz> stream();
}
