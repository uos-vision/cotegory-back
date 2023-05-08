package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vision.cotegory.controller.request.*;
import vision.cotegory.controller.response.DuplicateCheckResponse;
import vision.cotegory.controller.response.ExistBaekjoonHandleResponse;
import vision.cotegory.service.MemberService;
import vision.cotegory.service.dto.RegisterDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final MemberService memberService;

    @Operation(description = "[Member] loginId:member pw:1111\\\n[Admin] loginId:admin pw:1111")
    @PostMapping("/generateToken")
    public void generateToken(@RequestBody @Valid GenerateTokenRequest generateTokenRequest) {
    }

    @PostMapping("/refreshToken")
    public void refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequest registerRequest) {

        RegisterDto registerDto = RegisterDto.builder()
                .baekjoonHandle(registerRequest.getBaekjoonHandle())
                .loginId(registerRequest.getLoginId())
                .pw(registerRequest.getPw())
                .nickName(registerRequest.getNickName())
                .build();

        memberService.register(registerDto);
    }

    @PostMapping("/duplicate-check")
    public ResponseEntity<DuplicateCheckResponse> duplicatedCheck(
            @RequestBody @Valid DuplicateCheckRequest duplicateCheckRequest) {
        Boolean duplicated = memberService.isDuplicatedLoginId(duplicateCheckRequest.getLoginId());

        return ResponseEntity.ok(DuplicateCheckResponse.builder().duplicated(duplicated).build());
    }

    @PostMapping("/baekjoonhandle-check")
    public ResponseEntity<ExistBaekjoonHandleResponse> duplicatedCheck(
            @RequestBody @Valid ExistBaekjoonHandleRequest existBaekjoonHandleRequest) {
        Boolean exist = memberService.isExistBaekjoonHandle(existBaekjoonHandleRequest.getBaekjoonHandle());

        return ResponseEntity.ok(ExistBaekjoonHandleResponse.builder().exist(exist).build());
    }
}
