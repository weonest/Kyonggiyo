package kyonggiyo.application.service.event;

import kyonggiyo.application.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Slf4j
@Component
@RequiredArgsConstructor
public class ImageEventListener {

    private final ImageService imageService;

    @Async("EVENT_HANDLER_TASK_EXECUTOR")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createImage(ImageCreateEvent event) {
        imageService.createImage(event.imageUrls(), event.imageType(), event.referenceId());
    }

}
