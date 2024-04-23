package kyonggiyo.persistence.event.review;

import kyonggiyo.domain.event.ReviewEvent;

import java.util.List;

public interface ReviewEventRepository {

    ReviewEvent save(ReviewEvent event);

    ReviewEvent getById(Long id);

    List<ReviewEvent> findAllFailedEvent();

}
