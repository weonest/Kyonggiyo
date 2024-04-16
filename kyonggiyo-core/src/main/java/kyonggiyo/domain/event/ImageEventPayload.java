package kyonggiyo.domain.event;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kyonggiyo.domain.event.util.EventReasonConverter;
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

    @Convert(converter = EventReasonConverter.class, attributeName = "reason")
    private EventReason reason;

    private String imageUrls;

    @Builder
    private ImageEventPayload(EntityType entityType,
                              Long entityId,
                              EventReason reason,
                              String imageUrls) {
        validateEntityId(entityId);
        this.entityType = entityType;
        this.entityId = entityId;
        this.reason = reason;
        this.imageUrls = imageUrls;
    }

    private void validateEntityId(Long entityId) {
        if (Objects.isNull(entityId)) {
            throw new InvalidValueException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION);
        }
    }

}
