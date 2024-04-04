package kyonggiyo.application.port.out.event.image;

import kyonggiyo.domain.event.EventType;
import kyonggiyo.domain.event.ImageEvent;

import java.util.List;

public interface LoadImageEventPort {

    ImageEvent getById(Long id);

    List<ImageEvent> findAllByEventTypeAndStatus(EventType eventType, boolean status);

}
