package vision.cotegory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vision.cotegory.entity.TagGroup;

public interface TagGroupRepository extends JpaRepository<TagGroup, Long> {
}
