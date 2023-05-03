package vision.cotegory.exception.exception;

public class AIApiException extends BusinessException{
    public AIApiException(String message) {
        super(message);
    }

    public AIApiException() {
        super("AI 서버와 통신 에러가 발생했습니다");
    }
}
