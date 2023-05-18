package vision.cotegory.webclient.ai;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiRecommendProblemRequest {
    private String handle;
    private String tag;
    private Integer cnt;
    private String model;
}
