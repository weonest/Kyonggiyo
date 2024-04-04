package kyonggiyo.adapter.out.persistence.event.image;

import kyonggiyo.domain.event.EventType;
import kyonggiyo.domain.event.ImageEvent;

import java.util.List;

public interface ImageEventRepository {

    ImageEvent save(ImageEvent event);

    ImageEvent getById(Long id);

    List<ImageEvent> findAllByEventTypeAndStatus(EventType eventType, boolean status);

}
