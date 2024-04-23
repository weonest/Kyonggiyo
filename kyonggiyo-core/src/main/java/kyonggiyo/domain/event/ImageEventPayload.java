package kyonggiyo.domain.event;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kyonggiyo.domain.event.util.EventCommandConverter;
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
public class ImageEventPayload {

    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    private Long entityId;

    @Convert(converter = EventCommandConverter.class, attributeName = "reason")
    private EventCommand reason;

    private String imageUrls;

    @Builder
    private ImageEventPayload(EntityType entityType,
                              Long entityId,
                              EventCommand command,
                              String imageUrls) {
        validateEntityId(entityId);
        this.entityType = entityType;
        this.entityId = entityId;
        this.reason = command;
        this.imageUrls = imageUrls;
    }

    private void validateEntityId(Long entityId) {
        if (Objects.isNull(entityId)) {
            throw new InvalidValueException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION);
        }
    }

}
