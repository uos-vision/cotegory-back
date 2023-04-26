package vision.cotegory.exception.exception;

public class SolvedApiException extends BusinessException{
    public SolvedApiException(){
        super("Sovledac api 서버와 통신 에러가 발생했습니다");
    }

    public SolvedApiException(String message){
        super(message);
    }
}
