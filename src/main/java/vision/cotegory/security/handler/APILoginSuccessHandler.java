package vision.cotegory.security.handler;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import vision.cotegory.exception.exception.NotExistEntityException;
import vision.cotegory.repository.MemberRepository;
import vision.cotegory.utils.JWTUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtils jwtUtils;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Long memberId = memberRepository.getIdByLoginId(authentication.getName())
                .orElseThrow(NotExistEntityException::new);

        log.info("Login Success Handler......................");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        log.info(authentication);
        log.info(authentication.getName()); //username

        Map<String, Object> claim = Map.of(
                "loginId", authentication.getName(),
                "memberId", memberId
        );
        //Access Token 유효기간 1일
        String accessToken = jwtUtils.generateToken(claim, 1);
        //Refresh Token 유효기간 30일
        String refreshToken = jwtUtils.generateToken(claim, 2);

        Gson gson = new Gson();

        Map<String, String> keyMap = Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken,
                "expTimeAccessToken", jwtUtils.getExp(accessToken).toString(),
                "expTimeRefreshToken", jwtUtils.getExp(refreshToken).toString()
        );
        String jsonStr = gson.toJson(keyMap);

        response.setCharacterEncoding("utf-8");
        response.getWriter().println(jsonStr);
    }
}