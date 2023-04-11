package vision.cotegory.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vision.cotegory.exception.exception.BusinessException;
import vision.cotegory.exception.response.BusinessExceptionResponse;
import vision.cotegory.exception.response.RuntimeExceptionResponse;
import vision.cotegory.exception.response.SystemExceptionResponse;

import javax.transaction.SystemException;

@RestControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler
    public BusinessExceptionResponse businessExceptionResponse(BusinessException ex){
        return BusinessExceptionResponse.builder()
                .exceptionType("Business")
                .exceptionClassName(ex.getClass().getSimpleName())
                .exceptionMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler
    public RuntimeExceptionResponse businessExceptionResponse(RuntimeException ex){
        return RuntimeExceptionResponse.builder()
                .exceptionType("Runtime")
                .exceptionClassName(ex.getClass().getSimpleName())
                .exceptionMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler
    public SystemExceptionResponse systemExceptionResponse(SystemException ex){
        return SystemExceptionResponse.builder()
                .exceptionType("System")
                .exceptionClassName(ex.getClass().getSimpleName())
                .exceptionMessage(ex.getMessage())
                .build();
    }
}
