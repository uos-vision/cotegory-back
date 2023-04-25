package vision.cotegory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vision.cotegory.controller.request.DuplicateCheckRequest;
import vision.cotegory.controller.response.DuplicateCheckResponse;
import vision.cotegory.controller.request.LoginRequest;
import vision.cotegory.controller.request.RefreshTokenRequest;
import vision.cotegory.controller.request.RegisterRequest;
import vision.cotegory.service.MemberService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final MemberService memberService;

    @PostMapping("/generateToken")
    public void generateToken(@RequestBody @Valid LoginRequest loginRequest){
    }

    @PostMapping("/refreshToken")
    public void refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest){
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequest registerRequest) {
        memberService.register(registerRequest);
    }

    @GetMapping("/duplicate-check")
    public ResponseEntity<DuplicateCheckResponse> duplicatedCheck(
            @RequestBody @Valid DuplicateCheckRequest duplicateCheckRequest){
        Boolean duplicated = memberService.isDuplicatedLoginId(duplicateCheckRequest.getLoginId());

        return ResponseEntity.ok(DuplicateCheckResponse.builder().duplicated(duplicated).build());
    }
}
