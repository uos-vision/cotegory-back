package vision.cotegory.service.dto;

import lombok.Builder;
import lombok.Data;
import vision.cotegory.entity.Origin;

@Data
@Builder
public class CreateProblemMetaDto {
    private Integer problemNumber;

    private Origin origin;

    private String title;

    private String url;
}
