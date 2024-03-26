package kyonggiyo.application.service.event;

import kyonggiyo.application.service.image.ImageService;
import kyonggiyo.domain.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Slf4j
@Component
@RequiredArgsConstructor
public class ImageCreateEventListener {

    private final ImageService imageService;
    private final EventService<ImageCreateEvent> eventService;

    @Async("EVENT_HANDLER_TASK_EXECUTOR")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createImage(ImageCreateEvent imageCreateEvent) {
        String entityData = eventService.convertEventToJson(imageCreateEvent);
        Event event = eventService.createEvent(imageCreateEvent.toEvent(entityData));

        imageService.createImages(imageCreateEvent.imageUrls(),
                imageCreateEvent.imageType(),
                imageCreateEvent.referenceId());

        eventService.successEvent(event);
    }

}
