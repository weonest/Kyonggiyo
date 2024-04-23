package kyonggiyo.application.port.out.event.image;

import kyonggiyo.domain.event.ReviewEvent;

import java.util.List;

public interface LoadReviewEventPort {

    ReviewEvent getById(Long id);

    List<ReviewEvent> findAllFailedEvent();

}
