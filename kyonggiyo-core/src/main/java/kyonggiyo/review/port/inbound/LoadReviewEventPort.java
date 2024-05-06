package kyonggiyo.review.port.inbound;

import kyonggiyo.review.domain.event.ReviewEvent;

import java.util.List;

public interface LoadReviewEventPort {

    ReviewEvent getById(Long id);

    List<ReviewEvent> findAllFailedEvent();

}
