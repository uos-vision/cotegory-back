package vision.cotegory.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vision.cotegory.controller.request.RegisterRequest;
import vision.cotegory.controller.response.CurrentIdResponse;
import vision.cotegory.entity.Member;
import vision.cotegory.security.JWTUtil;
import vision.cotegory.service.MemberService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Transactional
public class MemberRestController {

    private final JWTUtil jwtUtil;

    @GetMapping("/current-id")
    public ResponseEntity<CurrentIdResponse> currentId(@RequestHeader(value = "Authorization") String jwtKey) {
        return ResponseEntity.ok(CurrentIdResponse.builder()
                .currentId(String.valueOf(jwtUtil.getMemberId(jwtKey)))
                .build());
    }
}
