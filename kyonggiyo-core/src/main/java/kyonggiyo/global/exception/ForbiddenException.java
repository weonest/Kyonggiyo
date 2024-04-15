package kyonggiyo.global.exception;

public class ForbiddenException extends BusinessException {

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ForbiddenException(ErrorCode errorCode, String loggingMessage) {
        super(errorCode, loggingMessage);
    }

}

