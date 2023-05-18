package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.tag.TagGroup;

import java.util.Optional;

public interface TagGroupRepository extends JpaRepository<TagGroup, Long> {
    Optional<TagGroup> findByName(String name);
}
