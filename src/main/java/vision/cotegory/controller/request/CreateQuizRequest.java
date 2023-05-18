package vision.cotegory.controller.request;

import lombok.Data;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.entity.problem.ProblemContents;
import vision.cotegory.entity.problem.ProblemMetaContents;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class CreateQuizRequest {
    @NotNull
    private ProblemMetaContents problemMetaContents;
    @NotNull
    private ProblemContents problemContents;

    @NotEmpty
    private Set<Tag> tags;
    @NotNull
    private Tag answerTag;
    @NotNull
    private Long tagGroupId;
}
