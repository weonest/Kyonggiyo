package kyonggiyo.review.domain.event;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import kyonggiyo.common.exception.GlobalErrorCode;
import kyonggiyo.common.exception.InvalidValueException;
import kyonggiyo.domain.event.EventCommand;
import kyonggiyo.domain.event.util.EventCommandConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEventPayload {

    private Long entityId;

    @Convert(converter = EventCommandConverter.class, attributeName = "reason")
    private EventCommand command;

    private String attribute;

    @Builder
    private ReviewEventPayload(Long entityId,
                               EventCommand command,
                               String attribute) {
        validateEntityId(entityId);
        this.entityId = entityId;
        this.command = command;
        this.attribute = attribute;
    }

    private void validateEntityId(Long entityId) {
        if (Objects.isNull(entityId)) {
            throw new InvalidValueException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION);
        }
    }

}
