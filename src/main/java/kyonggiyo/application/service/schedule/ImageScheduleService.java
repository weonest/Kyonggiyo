package kyonggiyo.application.service.schedule;

import kyonggiyo.adapter.out.client.image.PresignedUrlProvider;
import kyonggiyo.application.port.out.event.LoadEventPort;
import kyonggiyo.application.port.out.event.UpdateEventPort;
import kyonggiyo.application.port.out.image.SaveImagePort;
import kyonggiyo.application.service.event.EventService;
import kyonggiyo.application.service.event.ImageCreateEvent;
import kyonggiyo.domain.event.Event;
import kyonggiyo.domain.event.EventType;
import kyonggiyo.domain.image.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageScheduleService {

    private final LoadEventPort loadEventPort;
    private final SaveImagePort saveImagePort;
    private final UpdateEventPort updateEventPort;
    private final PresignedUrlProvider presignedUrlProvider;
    private final EventService<ImageCreateEvent> eventService;

    @Transactional
    @Scheduled(cron = "0 0/5 * * * *")
    public void findAllFailedImageCreateEventAndRetry() {
        List<Event> events = loadEventPort.findAllByEventTypeAndStatus(EventType.IMAGE_CREATE, false);

        if (events.isEmpty()) return;

        List<ImageCreateEvent> imageCreateEvents = events.stream()
                .map(eventService::convertDataToEntity)
                .toList();

        List<Image> images = new ArrayList<>();
        for (ImageCreateEvent imageCreateEvent : imageCreateEvents) {
            for (String url : imageCreateEvent.imageUrls()) {
                images.add(new Image(presignedUrlProvider.extractImageKey(url),
                        imageCreateEvent.imageType(),
                        imageCreateEvent.referenceId()));
            }
        }
        saveImagePort.saveAllInBatch(images);

        List<Long> entityIds = events.stream()
                .map(v -> v.getId())
                .toList();
        updateEventPort.updateStatusIdIn(entityIds);
    }

}
