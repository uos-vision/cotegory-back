package vision.cotegory.security.exception;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import vision.cotegory.exception.response.BusinessExceptionResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RefreshTokenException extends RuntimeException{

    private ErrorCase errorCase;

    public enum ErrorCase {
        NO_ACCESS, NO_REFRESH, OLD_REFRESH
    }

    public RefreshTokenException(ErrorCase errorCase){
        super(errorCase.name());
        this.errorCase = errorCase;
    }

    public void sendResponseError(HttpServletResponse response){

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        final BusinessExceptionResponse refreshTokenExceptionResponse = BusinessExceptionResponse.builder()
                .exceptionType("RefreshToken")
                .exceptionClassName(this.getClass().getSimpleName())
                .exceptionMessage(errorCase.name())
                .build();

        String responseStr = gson.toJson(refreshTokenExceptionResponse);

        try {
            response.getWriter().println(responseStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}