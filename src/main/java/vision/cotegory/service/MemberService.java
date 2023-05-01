package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Role;
import vision.cotegory.exception.exception.DuplicatedEntityException;
import vision.cotegory.repository.MemberRepository;
import vision.cotegory.service.dto.RegisterDto;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member register(RegisterDto registerDto) {

        Optional<Member> memberOptional = memberRepository
                .findByLoginIdAndActivatedIsTrue(registerDto.getLoginId());

        if (memberOptional.isPresent())
            throw new DuplicatedEntityException("로그인아이디가 중복됩니다");

        Member member = Member.builder()
                .loginId(registerDto.getLoginId())
                .baekjoonHandle(registerDto.getBaekjoonHandle())
                .pw(passwordEncoder.encode(registerDto.getPw()))
                .nickName(registerDto.getNickName())
                .roles(Set.of(Role.ROLE_USER))
                .activated(true)
                .build();

        return memberRepository.save(member);
    }

    public Boolean isDuplicatedLoginId(String loginId) {
        Optional<Member> memberOptional = memberRepository
                .findByLoginIdAndActivatedIsTrue(loginId);
        return memberOptional.isPresent();
    }
}
