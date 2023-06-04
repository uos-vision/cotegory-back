package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.tag.TagGroup;

import java.util.Map;

@Data
public class RankResponse {
    Long memberId;
    Map<TagGroup, Integer> rank;
}
