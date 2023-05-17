package vision.cotegory.controller.request;

import lombok.Builder;
import lombok.Data;
import vision.cotegory.entity.Origin;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateCompanyProblemRequest {

    @NotBlank(message = "ProblemName is required")
    private String problemName;

    @NotNull(message = "ProblemNum is required")
    private Integer problemNum;

    @NotNull(message = "Problemtype is required")
    private Origin origin;
}
