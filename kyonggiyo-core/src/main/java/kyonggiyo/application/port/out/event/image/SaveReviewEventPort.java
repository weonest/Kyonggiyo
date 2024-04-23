package kyonggiyo.application.port.out.event.image;

import kyonggiyo.domain.event.ReviewEvent;

public interface SaveReviewEventPort {

    ReviewEvent save(ReviewEvent event);

}
