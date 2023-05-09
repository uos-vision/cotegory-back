package vision.cotegory.initializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Role;
import vision.cotegory.entity.TagGroup;
import vision.cotegory.entity.TagGroupConst;
import vision.cotegory.repository.MemberRepository;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberInitializer {

    private final MemberRepository memberRepository;
    private final TagGroupConst tagGroupConst;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void memberInit() {
        makeUser();
        makeAdmin();
    }

    private void makeUser() {
        Member member = Member.builder()
                .loginId("member")
                .pw(passwordEncoder.encode("1111"))
                .roles(Set.of(Role.ROLE_USER))
                .baekjoonHandle("tori1753")
                .activated(true)
                .mmr(Map.of(
                        tagGroupConst.getTagGroupConsts().get(0), 200,
                        tagGroupConst.getTagGroupConsts().get(1), 300,
                        tagGroupConst.getTagGroupConsts().get(2), 400))
                .nickName("UOS닉네임")
                .build();

        memberRepository.save(member);
        log.info("[memberInit] {}", member);
    }

    private void makeAdmin() {
        Member admin = Member.builder()
                .loginId("admin")
                .pw(passwordEncoder.encode("1111"))
                .roles(Set.of(Role.ROLE_ADMIN))
                .activated(true)
                .nickName("admin닉네임")
                .build();

        memberRepository.save(admin);
        log.info("[adminInit] {}", admin);
    }
}
