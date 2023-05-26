package vision.cotegory.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import vision.cotegory.controller.response.RecommendResponse;

import java.util.List;

@Data
@AllArgsConstructor
public class RecommendResponses {
    List<RecommendResponse> recommendResponses;
}
