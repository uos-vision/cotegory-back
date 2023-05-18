package vision.cotegory.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.entity.tag.TagGroup;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagGroupResponse {
    private Long tagGroupId;
    private String tagGroupName;
    private List<Tag> tags;

    public TagGroupResponse(TagGroup tagGroup) {
        this.tagGroupId = tagGroup.getId();
        this.tagGroupName = tagGroup.getName();
        this.tags = new ArrayList<>(tagGroup.getTags());
    }
}
