package vision.cotegory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vision.cotegory.exception.exception.BusinessException;
import vision.cotegory.exception.exception.NotExistBaekjoonHandleException;
import vision.cotegory.exception.response.BusinessExceptionResponse;
import vision.cotegory.exception.response.RuntimeExceptionResponse;
import vision.cotegory.exception.response.SystemExceptionResponse;

@RestControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BusinessExceptionResponse businessExceptionResponse(BusinessException ex){
        return BusinessExceptionResponse.builder()
                .exceptionType("Business")
                .exceptionClassName(ex.getClass().getSimpleName())
                .exceptionMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RuntimeExceptionResponse runtimeExceptionResponse(RuntimeException ex){
        return RuntimeExceptionResponse.builder()
                .exceptionType("Runtime")
                .exceptionClassName(ex.getClass().getSimpleName())
                .exceptionMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public SystemExceptionResponse ExceptionResponse(Exception ex){
        return SystemExceptionResponse.builder()
                .exceptionType("Error")
                .exceptionClassName(ex.getClass().getSimpleName())
                .exceptionMessage(ex.getMessage())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public SystemExceptionResponse ExceptionResponse(NotExistBaekjoonHandleException ex){
        return SystemExceptionResponse.builder()
                .exceptionType("Business")
                .exceptionClassName(ex.getClass().getSimpleName())
                .exceptionMessage(ex.getMessage())
                .build();
    }
}
