package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vision.cotegory.controller.request.RegisterRequest;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Role;
import vision.cotegory.exception.DuplicatedEntityException;
import vision.cotegory.repository.MemberRepository;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member register(RegisterRequest registerRequest) {

        Optional<Member> memberOptional = memberRepository
                .findByLoginIdAndActivated(registerRequest.getLoginId(), true);

        if (memberOptional.isPresent())
            throw new DuplicatedEntityException("로그인아이디가 중복됩니다");

        Member member = Member.builder()
                .loginId(registerRequest.getLoginId())
                .baekjoonHandle(registerRequest.getBaekjoonHandle())
                .pw(registerRequest.getPw())
                .roles(Set.of(Role.ROLE_USER))
                .activated(true)
                .build();

        return memberRepository.save(member);
    }

    public Boolean isDuplicatedLoginId(String loginId) {
        Optional<Member> memberOptional = memberRepository
                .findByLoginIdAndActivated(loginId, true);
        return memberOptional.isPresent();
    }
}
