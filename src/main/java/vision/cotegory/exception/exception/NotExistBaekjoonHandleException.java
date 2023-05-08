package vision.cotegory.exception.exception;

public class NotExistBaekjoonHandleException extends BusinessException {
    public NotExistBaekjoonHandleException() {
        super("존재하지 않는 백준 핸들입니다");
    }

    public NotExistBaekjoonHandleException(String message) {
        super(message);
    }
}
