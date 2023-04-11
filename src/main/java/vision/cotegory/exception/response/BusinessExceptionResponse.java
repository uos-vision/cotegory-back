package vision.cotegory.exception.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BusinessExceptionResponse {
    private String exceptionType;
    private String exceptionClassName;
    private String exceptionMessage;

    public BusinessExceptionResponse(String exceptionType, String exceptionClassName, String exceptionMessage) {
        this.exceptionType = exceptionType;
        this.exceptionClassName = exceptionClassName;
        this.exceptionMessage = exceptionMessage;
    }
}
