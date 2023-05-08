package vision.cotegory.crawler.ai;

import lombok.Data;

@Data
public class AiRecommendProblemRequest {
    private String handle;
    private String tag;
    private Integer cnt;
    private String model;
}
