package vision.cotegory.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentIdResponse {
    private String currentId;
}
