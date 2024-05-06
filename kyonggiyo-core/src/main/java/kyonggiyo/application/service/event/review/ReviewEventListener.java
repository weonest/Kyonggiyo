package kyonggiyo.application.service.event.review;

import kyonggiyo.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ReviewEventListener {

    private final ImageService imageService;
    private final ReviewEventService reviewEventService;

    @EventListener
    public void createEvent(ReviewCreateEvent reviewCreateEvent) {
        String imageUrls = reviewEventService.convertImageUrlsToString(reviewCreateEvent.imageUrls());
        reviewEventService.createEvent(reviewCreateEvent.toEvent(imageUrls));
    }

    @Async("EVENT_HANDLER_TASK_EXECUTOR")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCreateEvent(ReviewCreateEvent reviewCreateEvent) {
        imageService.createImages(reviewCreateEvent.imageUrls(),
                reviewCreateEvent.imageType(),
                reviewCreateEvent.referenceId());
        reviewEventService.successEvent(reviewCreateEvent.id());
    }

}
