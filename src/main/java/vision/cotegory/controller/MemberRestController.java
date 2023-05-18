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

    private final MemberService memberService;

    @GetMapping("/information")
    public ResponseEntity<MemberInformationResponse> currentMemberInformation(@RequestHeader(value = "Authorization") Member member) {
        return ResponseEntity.ok(new MemberInformationResponse(member));
    }

    @Operation(summary = "profile='prod'에서만 정상적 호출이 가능합니다.")
    @PostMapping(value = "/img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void currentMemberInformation(@RequestHeader(value = "Authorization") Member member,
                                         @RequestPart MultipartFile multipartFile ) {
        memberService.uploadImage(member, multipartFile);
    }
}
