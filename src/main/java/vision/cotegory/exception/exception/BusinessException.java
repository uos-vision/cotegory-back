package vision.cotegory.exception.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(){
        super("비즈니스 예외입니다");
    }
}
