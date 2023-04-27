package vision.cotegory.exception.exception;

public class NotProperTagGroupAssignException extends RuntimeException{
    public NotProperTagGroupAssignException() {
        super("적절하지 않은 태그그룹 할당입니다");
    }

    public NotProperTagGroupAssignException(String message) {
        super(message);
    }
}
