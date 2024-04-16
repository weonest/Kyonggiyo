package kyonggiyo.persistence.event.image;

import kyonggiyo.domain.event.ImageEvent;
import kyonggiyo.common.exception.GlobalErrorCode;
import kyonggiyo.common.exception.NotFoundException;
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
    public List<ImageEvent> findAllFailedEvent() {
        return imageEventJpaRepository.findAllByStatus(false);
    }

}
