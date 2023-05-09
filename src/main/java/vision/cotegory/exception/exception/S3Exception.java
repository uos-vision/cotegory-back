package vision.cotegory.exception.exception;

public class S3Exception extends BusinessException{
    public S3Exception(String message, Throwable throwable) {
        super(message, throwable);
    }

    public S3Exception(Throwable throwable) {
        super("S3 서버와 통신 에러가 발생했습니다", throwable);
    }
}
