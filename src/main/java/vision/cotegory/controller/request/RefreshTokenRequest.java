package vision.cotegory.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
    @NotBlank
    private String accessToken;
}
