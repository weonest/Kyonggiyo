package kyonggiyo.domain.auth.exception;

import kyonggiyo.common.exception.AuthenticationException;
import kyonggiyo.common.exception.ErrorCode;

public class ExpiredTokenException extends AuthenticationException {

    public ExpiredTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ExpiredTokenException(ErrorCode errorCode, String loggingMessage) {
        super(errorCode, loggingMessage);
    }

}
