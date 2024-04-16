package kyonggiyo.domain.auth.exception;

import kyonggiyo.common.exception.ErrorCode;

public enum AccountErrorCode implements ErrorCode {

    ALREADY_HAS_USER("A001", "이미 유저 정보가 존재합니다.");

    private final String code;
    private final String message;

    AccountErrorCode(String code, String message) {
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
