package kyonggiyo.candidate.domain.vo;

import kyonggiyo.common.exception.GlobalErrorCode;
import kyonggiyo.common.exception.InvalidValueException;

import java.util.Arrays;

public enum Status {
    ACCEPTED,
    WAITING,
    ;

    public static Status from(String status) {
        return Arrays.stream(Status.values())
                .filter(v -> v.name().equals(status.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new InvalidValueException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION));
    }

}
