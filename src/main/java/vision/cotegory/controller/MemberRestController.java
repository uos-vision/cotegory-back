package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vision.cotegory.controller.response.CurrentIdResponse;
import vision.cotegory.controller.response.MemberInformationResponse;
import vision.cotegory.entity.Member;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.repository.MemberRepository;
import vision.cotegory.security.JWTUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Transactional
public class MemberRestController {

    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Operation(description = "테스트용도로만 사용하세요")
    @GetMapping("/current-id")
    public ResponseEntity<CurrentIdResponse> currentId(@RequestHeader(value = "Authorization") String jwtKey) {
        return ResponseEntity.ok(CurrentIdResponse.builder()
                .currentId(jwtUtil.getMemberId(jwtKey))
                .build());
    }

    @GetMapping("/information")
    public ResponseEntity<MemberInformationResponse> currentMemberInformation(@RequestHeader(value = "Authorization") String jwtKey) {
        Long memberId = jwtUtil.getMemberId(jwtKey);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotExistEntityException::new);

        return ResponseEntity.ok(MemberInformationResponse.builder()
                .id(member.getId())
                .roles(member.getRoles())
                .baekjoonHandle(member.getBaekjoonHandle())
                .nickName(member.getNickName())
                .imgUrl(member.getImgUrl())
                .build());
    }
}
