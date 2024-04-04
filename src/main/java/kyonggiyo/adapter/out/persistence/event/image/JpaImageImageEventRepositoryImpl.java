package kyonggiyo.adapter.out.persistence.event.image;

import kyonggiyo.domain.event.EventType;
import kyonggiyo.domain.event.ImageEvent;
import kyonggiyo.global.exception.GlobalErrorCode;
import kyonggiyo.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaImageImageEventRepositoryImpl implements ImageEventRepository {

    private final ImageEventJpaRepository imageEventJpaRepository;

    @Override
    public ImageEvent save(ImageEvent event) {
        return imageEventJpaRepository.save(event);
    }

    @Override
    public ImageEvent getById(Long id) {
        return imageEventJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(GlobalErrorCode.NOT_FOUND_ENTITY_EXCEPTION));
    }

    @Override
    public List<ImageEvent> findAllByEventTypeAndStatus(EventType eventType, boolean status) {
        return imageEventJpaRepository.findAllByEventTypeAndStatus(eventType, status);
    }

}
