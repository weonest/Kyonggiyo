package kyonggiyo.common.exception;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthenticationException(ErrorCode errorCode, String loggingMessage) {
        super(errorCode, loggingMessage);
    }

}
