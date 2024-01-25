package kyonggiyo.global.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String loggingMessage;

    protected BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.loggingMessage = null;
    }

    protected BusinessException(ErrorCode errorCode, String loggingMessage) {
        this.errorCode = errorCode;
        this.loggingMessage = loggingMessage;
    }

}
