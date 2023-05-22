package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vision.cotegory.controller.request.ChangeBaekjoonHandleRequest;
import vision.cotegory.controller.request.ChangeNicknameRequest;
import vision.cotegory.controller.response.MemberInformationResponse;
import vision.cotegory.entity.Member;
import vision.cotegory.exception.exception.NotExistBaekjoonHandleException;
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
    @PostMapping(value = "/img/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void imgUpdate(
            @RequestHeader(value = "Authorization") Member member,
            @RequestPart MultipartFile multipartFile) {
        memberService.uploadImage(member, multipartFile);
    }

    @Operation(summary = "profile='prod'에서만 정상적 호출이 가능합니다.")
    @PostMapping(value = "/img/delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void imgDelete(@RequestHeader(value = "Authorization") Member member) {
        memberService.deleteImage(member);
    }

    @PostMapping("/unregister")
    public void unregister(@RequestHeader(value = "Authorization") Member member) {
        member.setActivated(false);
    }

    @PostMapping("/change-nickname")
    public void changeNickname(
            @RequestHeader(value = "Authorization") Member member,
            @RequestBody ChangeNicknameRequest changeNicknameRequest) {
        member.setNickName(changeNicknameRequest.getNickname());
    }

    @PostMapping("/change-baekjoonhandle")
    public void changeBaekjoonHandle(
            @RequestHeader(value = "Authorization") Member member,
            @RequestBody ChangeBaekjoonHandleRequest changeBaekjoonHandleRequest) {
        String baekjoonHandle = changeBaekjoonHandleRequest.getBaekjoonHandle();
        if (memberService.isExistBaekjoonHandle(baekjoonHandle))
            throw new NotExistBaekjoonHandleException();
        member.setBaekjoonHandle(baekjoonHandle);
    }
}
