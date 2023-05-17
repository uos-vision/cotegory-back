package vision.cotegory.controller.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vision.cotegory.entity.Member;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.repository.MemberRepository;
import vision.cotegory.utils.JWTUtils;

@Component
@RequiredArgsConstructor
@Transactional
public class MemberFromHeaderConverter implements Converter<String, Member> {

    private final MemberRepository memberRepository;
    private final JWTUtils jwtUtils;

    @Override
    public Member convert(String Authorization) {
        Long memberId = jwtUtils.getMemberId(Authorization);
        return memberRepository.findById(memberId)
                .orElseThrow(NotExistEntityException::new);
    }
}
