package vision.cotegory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Role;
import vision.cotegory.repository.MemberRepository;

import java.util.Set;
import java.util.stream.IntStream;

@SpringBootTest
public class SandBox {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testInserts() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .loginId("member" + i)
                    .pw(passwordEncoder.encode("1111"))
                    .activated(true)
                    .nickName("nickname" + i)
                    .roles(Set.of(Role.ROLE_USER))
                    .build();
            memberRepository.save(member);
        });
    }
}
