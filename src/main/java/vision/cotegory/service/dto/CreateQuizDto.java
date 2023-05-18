package vision.cotegory.service.dto;

import lombok.Builder;
import lombok.Data;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.tag.Tag;
import vision.cotegory.entity.tag.TagGroup;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.util.Set;

@Data
@Builder
public class CreateQuizDto {
    private Tag answerTag;
    private TagGroup tagGroup;

    private Integer problemNumber;
    private Origin origin;
    private String title;
    private String url;

    private Set<Tag> tags;
    private String problemBody;
    private String problemInput;
    private String problemOutput;
    private String sampleInput;
    private String sampleOutput;
    private Integer timeLimit;
    private Integer memoryLimit;
}
