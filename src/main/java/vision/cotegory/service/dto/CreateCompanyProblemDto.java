package vision.cotegory.service.dto;

import lombok.Builder;
import lombok.Data;
import vision.cotegory.entity.Origin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateCompanyProblemDto {
    private String problemName;

    private Integer problemNum;

    private Origin origin;
}
