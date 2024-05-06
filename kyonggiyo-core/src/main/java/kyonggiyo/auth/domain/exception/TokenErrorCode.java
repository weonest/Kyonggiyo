package kyonggiyo.auth.domain.exception;

import kyonggiyo.common.exception.ErrorCode;

public enum TokenErrorCode implements ErrorCode {

    EXPIRED_TOKEN_EXCEPTION("T001", "만료된 토큰입니다."),
    INVALID_TOKEN_EXCEPTION("T002", "유효하지 않은 토큰입니다.");

    private final String code;
    private final String message;

    TokenErrorCode(String code, String message) {
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
