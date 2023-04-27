package vision.cotegory.service.dto;

import lombok.Builder;
import lombok.Data;
import vision.cotegory.entity.Tag;
import vision.cotegory.entity.TagGroup;

import java.util.Set;

@Data
@Builder
public class CreateQuizDto {
    private Integer problemNumber;
    private String title;
    private Set<Tag> tags;

    private Integer timeLimit;
    private Integer memoryLimit;

    private String problemBody;

    private String problemInput;
    private String problemOutput;
    private String sampleInput;
    private String sampleOutput;

    private Tag answerTag;
    private TagGroup tagGroup;
}
