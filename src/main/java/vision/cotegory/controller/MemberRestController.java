package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vision.cotegory.controller.response.MemberInformationResponse;
import vision.cotegory.entity.Member;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.repository.MemberRepository;
import vision.cotegory.utils.JWTUtils;
import vision.cotegory.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Transactional
public class MemberRestController {

    private final JWTUtils jwtUtils;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/information")
    public ResponseEntity<MemberInformationResponse> currentMemberInformation(@RequestHeader(value = "Authorization") String jwtKey) {
        Long memberId = jwtUtils.getMemberId(jwtKey);
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

    @Operation(summary = "profile='prod'에서만 정상적 호출이 가능합니다.")
    @PostMapping(value = "/img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void currentMemberInformation(@RequestHeader(value = "Authorization") String jwtKey,
                                         @RequestPart MultipartFile multipartFile ) {
        Long memberId = jwtUtils.getMemberId(jwtKey);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotExistEntityException::new);

        memberService.uploadImage(member, multipartFile);
    }
}
