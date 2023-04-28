package vision.cotegory.security.exception;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import vision.cotegory.exception.response.BusinessExceptionResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessTokenException extends RuntimeException {

    TOKEN_ERROR token_error;

    public enum TOKEN_ERROR {
        UNACCEPT(401, "Token is null or too short"),
        BADTYPE(401, "Token type Bearer"),
        MALFORM(403, "Malformed Token"),
        BADSIGN(403, "BadSignatured Token"),
        EXPIRED(403, "Expired Token");

        private int status;
        private String msg;

        TOKEN_ERROR(int status, String msg) {
            this.status = status;
            this.msg = msg;
        }

        public int getStatus() {
            return this.status;
        }

        public String getMsg() {
            return this.msg;
        }
    }

    public AccessTokenException(TOKEN_ERROR error) {
        super(error.name());
        this.token_error = error;
    }

    public void sendResponseError(HttpServletResponse response) {

        response.setStatus(token_error.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        final BusinessExceptionResponse accessTokenExceptionResponse = BusinessExceptionResponse.builder()
                .exceptionType("Business")
                .exceptionClassName(this.getClass().getSimpleName())
                .exceptionMessage(token_error.getMsg())
                .build();

        String responseStr = gson.toJson(accessTokenExceptionResponse);

        try {
            response.getWriter().println(responseStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
