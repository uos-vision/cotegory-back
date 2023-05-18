package vision.cotegory.controller.response;

import lombok.Builder;
import lombok.Data;
import vision.cotegory.entity.tag.Tag;

import java.util.List;

@Data
@Builder
public class TagsResponse {
    List<Tag> tags;
}
