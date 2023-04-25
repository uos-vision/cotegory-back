package vision.cotegory.exception.exception;

public class NotExistEntityException extends BusinessException {
    public NotExistEntityException() {
        super("해당 ID의 Entity가 존재하지 않습니다");
    }

    public NotExistEntityException(String message) {
        super(message);
    }
}
