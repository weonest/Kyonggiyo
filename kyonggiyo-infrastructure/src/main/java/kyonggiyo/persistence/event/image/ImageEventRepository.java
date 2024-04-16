package kyonggiyo.persistence.event.image;

import kyonggiyo.domain.event.ImageEvent;

import java.util.List;

public interface ImageEventRepository {

    ImageEvent save(ImageEvent event);

    ImageEvent getById(Long id);

    List<ImageEvent> findAllFailedEvent();

}
