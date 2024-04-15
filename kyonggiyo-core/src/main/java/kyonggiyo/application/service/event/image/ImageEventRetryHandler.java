package kyonggiyo.application.service.event.image;

import kyonggiyo.application.service.image.ImageService;
import kyonggiyo.domain.event.EntityType;
import kyonggiyo.domain.event.ImageEvent;
import kyonggiyo.domain.image.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ImageEventRetryHandler {

    private final ImageService imageService;
    private final ImageEventService imageEventService;

    @Transactional
    public void handle(ImageEvent event) {
        EntityType entityType = event.getPayload().getEntityType();

        switch (entityType) {
            case REVIEW -> {
                List<String> imageUrls = imageEventService.convertImageUrlsToList(event.getPayload().getImageUrls());
                imageService.createImages(imageUrls, ImageType.REVIEW, event.getPayload().getEntityId());
                imageEventService.successEvent(event.getId());
            }
        }
    }

}
