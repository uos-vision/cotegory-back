package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vision.cotegory.entity.tag.TagGroup;

import java.util.List;
import java.util.Optional;

public interface TagGroupRepository extends JpaRepository<TagGroup, Long> {
    Optional<TagGroup> findByName(String name);

    @Query("select tg from TagGroup tg left join fetch tg.quizzes")
    List<TagGroup> findAllFetchQuizzes();
}
