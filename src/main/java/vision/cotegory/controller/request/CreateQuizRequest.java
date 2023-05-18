package vision.cotegory.controller.request;

import lombok.Data;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.entity.tag.TagGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class CreateQuizRequest {
    @NotNull
    private Tag answerTag;
    @NotNull
    private Long tagGroupId;

    @NotNull
    private Integer problemNumber;
    @NotNull
    private Origin origin;
    @NotBlank
    private String title;
    @NotBlank
    private String url;

    @NotEmpty
    private Set<Tag> tags;
    @NotBlank
    private String problemBody;
    @NotBlank
    private String problemInput;
    @NotBlank
    private String problemOutput;
    @NotBlank
    private String sampleInput;
    @NotBlank
    private String sampleOutput;
    @NotNull
    private Integer timeLimit;
    @NotNull
    private Integer memoryLimit;
}
