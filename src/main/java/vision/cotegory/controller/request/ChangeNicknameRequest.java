package vision.cotegory.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangeNicknameRequest {
    @NotBlank
    private String nickname;
}
