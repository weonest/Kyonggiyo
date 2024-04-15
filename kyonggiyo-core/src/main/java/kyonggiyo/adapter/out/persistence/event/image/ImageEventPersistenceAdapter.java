package kyonggiyo.adapter.out.persistence.event.image;

import kyonggiyo.application.port.out.event.image.LoadImageEventPort;
import kyonggiyo.application.port.out.event.image.SaveImageEventPort;
import kyonggiyo.domain.event.ImageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ImageEventPersistenceAdapter implements SaveImageEventPort, LoadImageEventPort {

    private final ImageEventRepository imageEventRepository;

    @Override
    public ImageEvent save(ImageEvent event) {
        return imageEventRepository.save(event);
    }

    @Override
    public ImageEvent getById(Long id) {
        return imageEventRepository.getById(id);
    }

    @Override
    public List<ImageEvent> findAllFailedEvent() {
        return imageEventRepository.findAllFailedEvent();
    }

}
