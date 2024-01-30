package kyonggiyo.domain.auth.exception;

import kyonggiyo.global.exception.BusinessException;
import kyonggiyo.global.exception.ErrorCode;

public class ExpiredTokenException extends BusinessException {

    public ExpiredTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ExpiredTokenException(ErrorCode errorCode, String loggingMessage) {
        super(errorCode, loggingMessage);
    }

}
