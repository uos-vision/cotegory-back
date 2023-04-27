package vision.cotegory.exception.exception;

public class NotSupportedOriginException extends BusinessException{
    public NotSupportedOriginException(String message) {
        super(message);
    }

    public NotSupportedOriginException() {
        super("지원하지 않는 문제 Origin입니다");
    }
}
