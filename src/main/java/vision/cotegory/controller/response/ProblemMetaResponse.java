package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.Origin;
import vision.cotegory.entity.problem.ProblemMeta;

@Data
public class ProblemMetaResponse {
    private Integer problemNumber;
    private Origin origin;
    private String title;
    private String url;

    public ProblemMetaResponse(ProblemMeta problemMeta){
        this.problemNumber = problemMeta.getProblemNumber();
        this.origin = problemMeta.getOrigin();
        this.title = problemMeta.getTitle();
        this.url = problemMeta.getUrl();
    }

    public ProblemMetaResponse(Integer problemNumber, Origin origin, String title, String url) {
        this.problemNumber = problemNumber;
        this.origin = origin;
        this.title = title;
        this.url = url;
    }
}
