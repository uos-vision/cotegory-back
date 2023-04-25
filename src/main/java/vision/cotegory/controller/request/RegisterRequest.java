package vision.cotegory.controller.request;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterRequest {
    @NotBlank
    private String loginId;

    @NotBlank
    private String pw;

    @Nullable
    private String baekjoonHandle;

    @NotBlank
    private String nickName;
}
