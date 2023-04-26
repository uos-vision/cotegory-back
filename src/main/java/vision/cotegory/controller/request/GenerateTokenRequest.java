package vision.cotegory.controller.request;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GenerateTokenRequest {
    @NotBlank
    @ApiModelProperty(example = "member")
    private String loginId;

    @NotBlank
    @ApiModelProperty(example = "1111")
    private String pw;
}
