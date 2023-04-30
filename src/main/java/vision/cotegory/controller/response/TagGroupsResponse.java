package vision.cotegory.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TagGroupsResponse {
    List<TagGroupResponse> tagGroups;
}
