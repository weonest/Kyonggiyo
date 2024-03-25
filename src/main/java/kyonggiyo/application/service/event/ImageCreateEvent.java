package kyonggiyo.application.service.event;

import kyonggiyo.domain.event.EntityType;
import kyonggiyo.domain.event.EventType;
import kyonggiyo.domain.image.ImageType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ImageCreateEvent (
        EventType eventType,
        EntityType entityType,
        Long referenceId,
        ImageType imageType,
        List<MultipartFile> multipartFiles
) {

    public static ImageCreateEvent of(Long referenceId, ImageType imageType, List<MultipartFile> multipartFiles) {
        return new ImageCreateEvent(EventType.IMAGE_CREATE,
                EntityType.IMAGE,
                referenceId,
                imageType,
                multipartFiles);
    }

}
