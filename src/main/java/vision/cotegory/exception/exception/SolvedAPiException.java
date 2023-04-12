package vision.cotegory.exception.exception;

public class SolvedAPiException extends BusinessException{
    public SolvedAPiException(){
        super("Sovledac api 서버와 통신 에러가 발생했습니다");
    }
}
