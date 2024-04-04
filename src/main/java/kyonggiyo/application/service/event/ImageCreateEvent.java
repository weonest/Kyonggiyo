package kyonggiyo.application.service.event;

import kyonggiyo.domain.event.EntityType;
import kyonggiyo.domain.event.EventType;
import kyonggiyo.domain.event.ImageEvent;
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

    public ImageEvent toImageEvent(String imageUrls) {
        return ImageEvent.builder()
                .id(id)
                .eventType(EventType.IMAGE)
                .entityType(EntityType.valueOf(imageType.name().toUpperCase()))
                .entityId(referenceId)
                .reason("/리뷰이미지생성")
                .imageUrls(imageUrls)
                .build();
    }

}
