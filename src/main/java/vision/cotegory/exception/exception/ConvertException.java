package vision.cotegory.exception.exception;

public class ConvertException extends BusinessException{
    public ConvertException(){
        super("데이터형 변환 오류입니다");
    }
    public ConvertException(String message){
        super(message);
    }

}
