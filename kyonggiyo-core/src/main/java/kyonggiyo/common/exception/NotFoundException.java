package kyonggiyo.common.exception;

public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotFoundException(ErrorCode errorCode, String loggingMessage) {
        super(errorCode, loggingMessage);
    }

}
