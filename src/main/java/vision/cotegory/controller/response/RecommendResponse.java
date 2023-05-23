package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.problem.ProblemMeta;

@Data
public class RecommendResponse {
    private String title;
    private Integer problemNum;
    private String url;

    public RecommendResponse(ProblemMeta problemMeta) {
        this.title = problemMeta.getTitle();
        this.problemNum = problemMeta.getProblemNumber();
        this.url = problemMeta.getUrl();
    }
}
