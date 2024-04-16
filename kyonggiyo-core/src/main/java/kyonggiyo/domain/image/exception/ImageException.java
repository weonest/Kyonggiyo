package kyonggiyo.domain.image.exception;

import kyonggiyo.common.exception.BusinessException;
import kyonggiyo.common.exception.ErrorCode;

public class ImageException extends BusinessException {

    public ImageException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ImageException(ErrorCode errorCode, String loggingMessage) {
        super(errorCode, loggingMessage);
    }

}
