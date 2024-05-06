package kyonggiyo.review.port.outbound;

import kyonggiyo.review.domain.entity.Review;

public interface SaveReviewPort {

    Review save(Review review);

}
