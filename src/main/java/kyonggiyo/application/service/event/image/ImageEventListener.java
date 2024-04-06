package kyonggiyo.application.service.event.image;

import kyonggiyo.application.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ImageEventListener {

    private final ImageService imageService;
    private final ImageEventService imageEventService;

    @EventListener
    public void createEvent(ImageCreateEvent imageCreateEvent) {
        String imageUrls = imageEventService.convertImageUrlsToString(imageCreateEvent.imageUrls());
        imageEventService.createEvent(imageCreateEvent.toImageEvent(imageUrls));
    }

    @Async("EVENT_HANDLER_TASK_EXECUTOR")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createImage(ImageCreateEvent imageCreateEvent) {
        imageService.createImages(imageCreateEvent.imageUrls(),
                imageCreateEvent.imageType(),
                imageCreateEvent.referenceId());
        imageEventService.successEvent(imageCreateEvent.id());
    }

}
