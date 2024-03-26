package kyonggiyo.application.service.event;

import kyonggiyo.domain.event.EntityType;
import kyonggiyo.domain.event.Event;
import kyonggiyo.domain.event.EventType;
import kyonggiyo.domain.image.ImageType;

import java.util.List;

public record ImageCreateEvent(
        List<String> imageUrls,
        ImageType imageType,
        Long referenceId
) {

    public static ImageCreateEvent of(List<String> imageUrls, ImageType imageType, Long referenceId) {
        return new ImageCreateEvent(imageUrls,
                imageType,
                referenceId);
    }

    public Event toEvent(String entityData) {
        return Event.builder()
                .entityType(EntityType.REVIEW)
                .eventType(EventType.IMAGE_CREATE)
                .entityId(referenceId)
                .entityData(entityData)
                .build();
    }

}
