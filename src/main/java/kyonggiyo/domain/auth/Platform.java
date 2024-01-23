package kyonggiyo.domain.auth;

public enum Platform {
    NAVER,
    KAKAO;

    public static Platform from(String platform) {
        try {
            return Platform.valueOf(platform.toUpperCase());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException(); // 커스텀 예외로 변경 예정
        }
    }

}

