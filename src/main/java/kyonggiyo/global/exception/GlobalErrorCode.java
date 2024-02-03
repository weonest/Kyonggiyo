package kyonggiyo.global.exception;

public enum GlobalErrorCode implements ErrorCode {

    INTERNAL_SERVER_ERROR("G001", "서버에 알 수 없는 문제가 발생했습니다."),
    INVALID_REQUEST_ERROR("G002", "요청 정보가 유효하지 않습니다."),
    NO_AUTHENTICATION_INFO_ERROR("G003", "인증 정보가 존재하지 않습니다. 다시 로그인을 시도해주세요.");

    private final String code;
    private final String message;

    GlobalErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }

}
