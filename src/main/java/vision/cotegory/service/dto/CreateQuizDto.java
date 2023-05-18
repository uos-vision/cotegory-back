package vision.cotegory.service.dto;

import lombok.Builder;
import lombok.Data;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.entity.tag.TagGroup;
import vision.cotegory.entity.problem.ProblemContents;
import vision.cotegory.entity.problem.ProblemMetaContents;

import java.util.Set;

@Data
@Builder
public class CreateQuizDto {
    private ProblemMetaContents problemMetaContents;
    private ProblemContents problemContents;

    private Set<Tag> tags;
    private Tag answerTag;
    private TagGroup tagGroup;
}
