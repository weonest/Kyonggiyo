package kyonggiyo.application.service.event;

import kyonggiyo.domain.event.EntityType;
import kyonggiyo.domain.event.Event;
import kyonggiyo.domain.event.EventType;
import kyonggiyo.domain.image.ImageType;

import java.util.List;

public record ImageCreateEvent(
        Long id,
        List<String> imageUrls,
        ImageType imageType,
        Long referenceId
) {

    public static ImageCreateEvent of(Long id,
                                      List<String> imageUrls,
                                      ImageType imageType,
                                      Long referenceId) {
        return new ImageCreateEvent(id,
                imageUrls,
                imageType,
                referenceId);
    }

    public Event toEvent(String entityData) {
        return Event.builder()
                .id(id)
                .entityType(EntityType.REVIEW)
                .eventType(EventType.IMAGE_CREATE)
                .entityId(referenceId)
                .entityData(entityData)
                .build();
    }

}
