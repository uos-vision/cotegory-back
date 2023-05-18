package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.problem.ProblemMeta;
import vision.cotegory.entity.problem.ProblemMetaContents;

@Data
public class ProblemMetaResponse {
    private ProblemMetaContents problemMetaContents;

    public ProblemMetaResponse(ProblemMeta problemMeta){
        this.problemMetaContents = problemMeta.getProblemMetaContents();
    }

    public ProblemMetaResponse(Integer problemNumber, Origin origin, String title, String url) {
        this.problemMetaContents = ProblemMetaContents.builder()
                .problemNumber(problemNumber)
                .origin(origin)
                .title(title)
                .url(url)
                .build();
    }
}
