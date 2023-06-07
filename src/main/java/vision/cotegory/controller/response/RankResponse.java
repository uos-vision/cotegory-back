package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.tag.TagGroup;

import java.util.Map;

@Data
public class RankResponse {
    private Long memberId;
    private Map<TagGroup, Integer> rank;

    private Long MemberNum;
}
