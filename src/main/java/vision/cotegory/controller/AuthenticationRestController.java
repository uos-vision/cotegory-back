package vision.cotegory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    public void generateToken(@RequestBody LoginRequest loginRequest){
    }

    @PostMapping("/refreshToken")
    public void refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequest registerRequest) {
        memberService.register(registerRequest);
    }
}
