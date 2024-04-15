package kyonggiyo.domain.auth;

public enum Platform {
    NAVER,
    KAKAO;

    public static Platform from(String platform) {
        return Platform.valueOf(platform.toUpperCase());
    }

}

