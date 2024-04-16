package kyonggiyo.common.exception;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidValueException(ErrorCode errorCode, String loggingMessage) {
        super(errorCode, loggingMessage);
    }

}
