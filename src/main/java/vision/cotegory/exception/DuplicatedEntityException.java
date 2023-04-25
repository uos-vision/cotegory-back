package vision.cotegory.exception;

import vision.cotegory.exception.exception.BusinessException;

public class DuplicatedEntityException extends BusinessException {
    public DuplicatedEntityException(String message) {
        super(message);
    }

    public DuplicatedEntityException() {
        super("Entity가 중복됩니다");
    }
}
