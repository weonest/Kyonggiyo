package kyonggiyo.application.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.CompletableFuture;


@Component
@RequiredArgsConstructor
public class ImageEventListener {

    private final EventService<ImageCreateEvent> eventService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createImage(ImageCreateEvent imageCreateEvent) {
        CompletableFuture.supplyAsync(() -> {
            eventService.createEvent(imageCreateEvent);
        }).thenApply(event -> {

        }).thenAccept();
    }

}
