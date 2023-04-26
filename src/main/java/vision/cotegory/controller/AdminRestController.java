package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vision.cotegory.controller.response.CurrentIdResponse;
import vision.cotegory.repository.MemberRepository;
import vision.cotegory.security.JWTUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Transactional
@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
public class AdminRestController {

    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Operation(description = "테스트용도로만 사용하세요")
    @GetMapping("/current-id")
    public ResponseEntity<CurrentIdResponse> currentId(@RequestHeader(value = "Authorization") String jwtKey) {
        return ResponseEntity.ok(CurrentIdResponse.builder()
                .currentId(String.valueOf(jwtUtil.getMemberId(jwtKey)))
                .build());
    }
}
