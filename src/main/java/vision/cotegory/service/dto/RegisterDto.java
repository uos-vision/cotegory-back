package vision.cotegory.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDto {
    private String loginId;
    private String pw;
    private String baekjoonHandle;
    private String nickName;
}
