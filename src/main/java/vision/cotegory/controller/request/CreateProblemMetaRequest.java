package vision.cotegory.controller.request;

import lombok.Data;
import vision.cotegory.entity.problem.ProblemMetaContents;

@Data
public class CreateProblemMetaRequest {
    private ProblemMetaContents problemMetaContents;
}
