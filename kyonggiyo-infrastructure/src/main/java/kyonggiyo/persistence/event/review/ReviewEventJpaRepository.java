package kyonggiyo.persistence.event.review;

import kyonggiyo.review.domain.event.ReviewEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewEventJpaRepository extends JpaRepository<ReviewEvent, Long> {

    List<ReviewEvent> findAllByStatus(boolean status);

}
