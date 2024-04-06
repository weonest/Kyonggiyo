package kyonggiyo.domain.event;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kyonggiyo.domain.event.util.EventReasonConverter;
import kyonggiyo.global.exception.GlobalErrorCode;
import kyonggiyo.global.exception.InvalidValueException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventPayload {

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    private Long entityId;

    @Convert(converter = EventReasonConverter.class, attributeName = "reason")
    private EventReason reason;

    @Builder
    private EventPayload(EventType eventType,
                         EntityType entityType,
                         Long entityId,
                         EventReason reason) {
        validateEntityId(entityId);
        this.eventType = eventType;
        this.entityType = entityType;
        this.entityId = entityId;
        this.reason = reason;
    }

    private void validateEntityId(Long entityId) {
        if (Objects.isNull(entityId)) {
            throw new InvalidValueException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION);
        }
    }

}