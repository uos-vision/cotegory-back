package vision.cotegory.webclient.ai;

import lombok.Data;

@Data
public class AiRecommendProblemRequest {
    private String handle;
    private String tag;
    private Integer cnt;
    private String model;
}
