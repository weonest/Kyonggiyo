package kyonggiyo.domain.event;

import kyonggiyo.common.exception.GlobalErrorCode;
import kyonggiyo.common.exception.InvalidValueException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum EventReason {

    REVIEW_IMAGE_CREATE("/리뷰이미지생성")
    ;

    private final String reason;

    EventReason(String reason) {
        this.reason = reason;
    }

    public static EventReason from(String reason) {
        return Arrays.stream(EventReason.values())
                .filter(v -> v.getReason().equals(reason))
                .findAny()
                .orElseThrow(() -> new InvalidValueException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION));
    }

}
