package vision.cotegory.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import vision.cotegory.entity.Origin;

import java.util.List;

@Data
@AllArgsConstructor
public class OriginListResponse {
    List<Origin> origins;
}
