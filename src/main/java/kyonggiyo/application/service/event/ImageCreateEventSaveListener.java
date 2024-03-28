package kyonggiyo.application.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ImageCreateEventSaveListener {

    private final EventService<ImageCreateEvent> eventService;

    @EventListener
    public void createEvent(ImageCreateEvent imageCreateEvent) {
        String entityData = eventService.convertEventToJson(imageCreateEvent);
        eventService.createEvent(imageCreateEvent.toEvent(entityData));
    }

}
