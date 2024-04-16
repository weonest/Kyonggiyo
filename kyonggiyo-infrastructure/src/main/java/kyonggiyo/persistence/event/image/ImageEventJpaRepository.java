package kyonggiyo.persistence.event.image;

import kyonggiyo.domain.event.ImageEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageEventJpaRepository extends JpaRepository<ImageEvent, Long> {

    List<ImageEvent> findAllByStatus(boolean status);

}
