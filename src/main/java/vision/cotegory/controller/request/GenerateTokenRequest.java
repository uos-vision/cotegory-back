package vision.cotegory.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GenerateTokenRequest {
    @NotBlank
    @Schema(example = "member")
    private String loginId;

    @NotBlank
    @Schema(example = "1111")
    private String pw;
}
