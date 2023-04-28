package vision.cotegory.security.handler;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import vision.cotegory.exception.response.BusinessExceptionResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class APILoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("utf-8");

        Gson gson = new Gson();
        final BusinessExceptionResponse refreshTokenExceptionResponse = BusinessExceptionResponse.builder()
                .exceptionType("Business")
                .exceptionClassName(exception.getClass().getSimpleName())
                .exceptionMessage(exception.getMessage())
                .build();

        String responseStr = gson.toJson(refreshTokenExceptionResponse);
        try {
            response.getWriter().println(responseStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
