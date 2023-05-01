package vision.cotegory.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import vision.cotegory.entity.Member;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class APIUserDTO extends User {

    private String loginId;
    private String pw;

    public APIUserDTO(Member member) {
        super(
                member.getLoginId(),
                member.getPw(),
                member.getActivated(),
                true,
                true,
                true,
                member.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList())
        );
        this.loginId = member.getLoginId();
        this.pw = member.getPw();
    }
}