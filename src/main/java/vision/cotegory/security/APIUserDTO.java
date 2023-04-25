package vision.cotegory.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class APIUserDTO extends User {

    private String loginId;
    private String pw;

    public APIUserDTO(String username, String password, Collection<GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
        this.loginId = username;
        this.pw = password;
    }
}