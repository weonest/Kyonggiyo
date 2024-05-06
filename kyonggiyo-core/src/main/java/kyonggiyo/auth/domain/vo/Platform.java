package kyonggiyo.auth.domain.vo;

import kyonggiyo.common.exception.GlobalErrorCode;
import kyonggiyo.common.exception.InvalidValueException;

import java.util.Arrays;

public enum Platform {
    NAVER,
    KAKAO;

    public static Platform from(String platform) {
        return Arrays.stream(Platform.values())
                .filter(v -> v.name().equals(platform.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new InvalidValueException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION));
    }

}

