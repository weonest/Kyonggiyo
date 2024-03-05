package kyonggiyo.domain.image.exception;

import kyonggiyo.global.exception.BusinessException;
import kyonggiyo.global.exception.ErrorCode;

public class ImageException extends BusinessException {

    public ImageException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ImageException(ErrorCode errorCode, String loggingMessage) {
        super(errorCode, loggingMessage);
    }

}
