package kyonggiyo.domain.auth.exception;

import kyonggiyo.global.exception.AuthenticationException;
import kyonggiyo.global.exception.ErrorCode;

public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidTokenException(ErrorCode errorCode, String loggingMessage) {
        super(errorCode, loggingMessage);
    }

}
