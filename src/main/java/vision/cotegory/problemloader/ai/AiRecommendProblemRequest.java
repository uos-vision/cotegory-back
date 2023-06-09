package vision.cotegory.problemloader.ai;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiRecommendProblemRequest {
    private String handle;
    private String tag;
    @Builder.Default
    private Integer cnt = 1;
    @Builder.Default
    private String model = "AUTO_ENCODER";
}
