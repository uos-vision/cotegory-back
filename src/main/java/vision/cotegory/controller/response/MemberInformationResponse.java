package vision.cotegory.controller.response;

import lombok.Builder;
import lombok.Data;
import vision.cotegory.entity.Role;

import java.util.Set;

@Data
@Builder
public class MemberInformationResponse {
    private Long id;
    private String baekjoonHandle;
    private String imgUrl;
    private String nickName;
    private Set<Role> roles;
}
