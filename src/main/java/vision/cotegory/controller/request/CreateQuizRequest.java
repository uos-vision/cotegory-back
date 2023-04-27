package vision.cotegory.controller.request;

import lombok.Data;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.Tag;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class CreateQuizRequest {
    @NotNull
    private Integer problemNumber;

    @NotEmpty
    private String title;
    private Set<Tag> tags;
    @NotNull
    private Integer timeLimit;
    @NotNull
    private Integer memoryLimit;
    @NotEmpty
    private String problemBody;

    @NotEmpty
    private String problemInput;
    @NotEmpty
    private String problemOutput;
    @NotEmpty
    private String sampleInput;
    @NotEmpty
    private String sampleOutput;

    @NotNull
    private Tag answerTag;
    @NotNull
    private Long tagGroupId;

    @NotNull
    Origin origin;
}
