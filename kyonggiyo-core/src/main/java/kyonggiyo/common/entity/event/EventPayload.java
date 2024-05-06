package kyonggiyo.common.entity.event;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kyonggiyo.common.entity.event.util.EventCommandConverter;
import kyonggiyo.common.exception.GlobalErrorCode;
import kyonggiyo.common.exception.InvalidValueException;
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

    @Convert(converter = EventCommandConverter.class, attributeName = "reason")
    private EventCommand reason;

    @Builder
    private EventPayload(EventType eventType,
                         EntityType entityType,
                         Long entityId,
                         EventCommand command) {
        validateEntityId(entityId);
        this.eventType = eventType;
        this.entityType = entityType;
        this.entityId = entityId;
        this.reason = command;
    }

    private void validateEntityId(Long entityId) {
        if (Objects.isNull(entityId)) {
            throw new InvalidValueException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION);
        }
    }

}
