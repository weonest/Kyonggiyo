package kyonggiyo.domain.event;

import kyonggiyo.common.exception.GlobalErrorCode;
import kyonggiyo.common.exception.InvalidValueException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum EventCommand {

    REVIEW_IMAGE_CREATE("/리뷰이미지생성")
    ;

    private final String command;

    EventCommand(String command) {
        this.command = command;
    }

    public static EventCommand from(String reason) {
        return Arrays.stream(EventCommand.values())
                .filter(v -> v.getCommand().equals(reason))
                .findAny()
                .orElseThrow(() -> new InvalidValueException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION));
    }

}
