package vision.cotegory.controller.response;

import lombok.Data;
import vision.cotegory.entity.Recommend;
import vision.cotegory.entity.RecommendType;
import vision.cotegory.entity.problem.ProblemMeta;

@Data
public class RecommendResponse {
    private String title;
    private Integer problemNum;
    private String url;
    private RecommendType recommendType;

    public RecommendResponse(Recommend recommend) {
        this.title = recommend.getProblemMeta().getTitle();
        this.problemNum = recommend.getProblemMeta().getProblemNumber();
        this.url = recommend.getProblemMeta().getUrl();
        this.recommendType = recommend.getRecommendType();
    }
}
