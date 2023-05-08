package vision.cotegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import vision.cotegory.entity.Member;
import vision.cotegory.entity.Role;
import vision.cotegory.entity.TagGroupConst;
import vision.cotegory.exception.exception.DuplicatedEntityException;
import vision.cotegory.exception.exception.NotExistBaekjoonHandleException;
import vision.cotegory.repository.MemberRepository;
import vision.cotegory.service.dto.RegisterDto;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TagGroupConst tagGroupConst;
    private final WebClient webClient;

    public Member register(RegisterDto registerDto) {

        Optional<Member> memberOptional = memberRepository
                .findByLoginIdAndActivatedIsTrue(registerDto.getLoginId());

        if (memberOptional.isPresent())
            throw new DuplicatedEntityException("로그인아이디가 중복됩니다");

        validateBaekjoonHandle(registerDto.getBaekjoonHandle());


        Member member = Member.builder()
                .loginId(registerDto.getLoginId())
                .baekjoonHandle(registerDto.getBaekjoonHandle())
                .mmr(tagGroupConst.getTagGroupConsts().stream().collect(Collectors.toMap(
                        v1 -> v1,
                        v1 -> 1200
                )))
                .pw(passwordEncoder.encode(registerDto.getPw()))
                .nickName(registerDto.getNickName())
                .roles(Set.of(Role.ROLE_USER))
                .activated(true)
                .build();

        return memberRepository.save(member);
    }

    private void validateBaekjoonHandle(String baekjoonHandle) {
        try{
            HttpStatus statusCode = webClient.mutate()
                    .baseUrl("https://www.acmicpc.net")
                    .build()
                    .get()
                    .uri(String.format("/user/%s", baekjoonHandle))
                    .retrieve()
                    .toBodilessEntity()
                    .block()
                    .getStatusCode();
        }catch(Exception ex) {
            throw new NotExistBaekjoonHandleException();
        }
    }

    public Boolean isDuplicatedLoginId(String loginId) {
        Optional<Member> memberOptional = memberRepository
                .findByLoginIdAndActivatedIsTrue(loginId);
        return memberOptional.isPresent();
    }
}
