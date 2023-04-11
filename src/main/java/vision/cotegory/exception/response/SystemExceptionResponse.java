package vision.cotegory.exception.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemExceptionResponse extends Exception{
    private String exceptionType;
    private String exceptionClassName;
    private String exceptionMessage;

    public SystemExceptionResponse(String exceptionType, String exceptionClassName, String exceptionMessage) {
        this.exceptionType = exceptionType;
        this.exceptionClassName = exceptionClassName;
        this.exceptionMessage = exceptionMessage;
    }
}
