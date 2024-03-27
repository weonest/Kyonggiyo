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
public class ImageCreateEventListener {

    private final ImageService imageService;
    private final EventService<ImageCreateEvent> eventService;

    @Async("EVENT_HANDLER_TASK_EXECUTOR")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createImage(ImageCreateEvent imageCreateEvent) {
        imageService.createImages(imageCreateEvent.imageUrls(),
                imageCreateEvent.imageType(),
                imageCreateEvent.referenceId());
        // 이미지 저장 성공시 이벤트 상태 변경
        eventService.successEvent(imageCreateEvent.id());
    }

}
