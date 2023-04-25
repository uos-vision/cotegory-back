package vision.cotegory.controller.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
    private String accessToken;
}
