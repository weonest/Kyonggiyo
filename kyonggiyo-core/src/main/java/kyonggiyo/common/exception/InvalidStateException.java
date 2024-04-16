package kyonggiyo.common.exception;

public class InvalidStateException extends BusinessException {

    public InvalidStateException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidStateException(ErrorCode errorCode, String loggingMessage) {
        super(errorCode, loggingMessage);
    }

}
