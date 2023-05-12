package vision.cotegory.exception.exception;

public class AiApiException extends BusinessException{
    public AiApiException(String message) {
        super(message);
    }

    public AiApiException() {
        super("AI 서버와 통신 에러가 발생했습니다");
    }
}
