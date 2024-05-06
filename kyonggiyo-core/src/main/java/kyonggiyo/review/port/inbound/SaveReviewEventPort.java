package kyonggiyo.review.port.inbound;

import kyonggiyo.review.domain.event.ReviewEvent;

public interface SaveReviewEventPort {

    ReviewEvent save(ReviewEvent event);

}
