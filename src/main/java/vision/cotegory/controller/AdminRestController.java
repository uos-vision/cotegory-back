package vision.cotegory.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vision.cotegory.controller.response.AbnormalQuizResponse;
import vision.cotegory.controller.response.CurrentIdResponse;
import vision.cotegory.repository.AbnormalQuizRepository;
import vision.cotegory.security.JWTUtil;
import vision.cotegory.service.AbnormalQuizService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Transactional
@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
public class AdminRestController {

    private final JWTUtil jwtUtil;
    private final AbnormalQuizService abnormalQuizService;
    private final AbnormalQuizRepository abnormalQuizRepository;

    @Operation(description = "테스트용도로만 사용하세요")
    @GetMapping("/current-id")
    public ResponseEntity<CurrentIdResponse> currentId(@RequestHeader(value = "Authorization") String jwtKey) {
        return ResponseEntity.ok(CurrentIdResponse.builder()
                .currentId(jwtUtil.getMemberId(jwtKey))
                .build());
    }

    @PostMapping("/abnormal/update")
    public void updateAbnormal(){
        abnormalQuizService.updateAbnormalQuizzes();
    }

    @PostMapping("/abnormal/list")
    public Page<AbnormalQuizResponse> listAbnormal(Pageable pageable){
        return abnormalQuizRepository.findAll(pageable).map(AbnormalQuizResponse::new);
    }
}
