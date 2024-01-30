package kyonggiyo.domain.auth.exception;

import kyonggiyo.global.exception.BusinessException;
import kyonggiyo.global.exception.ErrorCode;

public class InvalidJwtException extends BusinessException {

    public InvalidJwtException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidJwtException(ErrorCode errorCode, String loggingMessage) {
        super(errorCode, loggingMessage);
    }

}
