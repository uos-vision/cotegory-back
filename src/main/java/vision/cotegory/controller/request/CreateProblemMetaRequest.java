package vision.cotegory.controller.request;

import lombok.Data;
import vision.cotegory.entity.Origin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateProblemMetaRequest {
    @NotNull
    private Integer problemNumber;

    @NotNull
    private Origin origin;

    @NotBlank
    private String title;

    @NotBlank
    private String url;

    @NotNull
    private Boolean isCompany;
}
