package kyonggiyo.domain.image.exception;

import kyonggiyo.common.exception.ErrorCode;

public enum ImageErrorCode implements ErrorCode {

    INVALID_FILE_EXTENSION("I001", "잘못된 파일 확장자입니다."),
    UPLOAD_EXCEPTION("I002", "이미지 업로드에 실패하였습니다."),
    DELETE_EXCEPTION("I003", "이미지 삭제에 실패하였습니다.")
    ;

    private final String code;
    private final String message;

    ImageErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}
