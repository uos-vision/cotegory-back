package vision.cotegory.controller.request;

import lombok.Data;
import vision.cotegory.entity.Origin;

@Data
public class CreateProblemMetaRequest {
    private Integer problemNumber;

    private Origin origin;

    private String title;

    private String url;

    private Boolean isCompany;
}
