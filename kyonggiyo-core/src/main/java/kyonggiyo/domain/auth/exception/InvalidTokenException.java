package kyonggiyo.domain.auth.exception;

import kyonggiyo.common.exception.AuthenticationException;
import kyonggiyo.common.exception.ErrorCode;

public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidTokenException(ErrorCode errorCode, String loggingMessage) {
        super(errorCode, loggingMessage);
    }

}
