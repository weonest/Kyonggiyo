package kyonggiyo.application.port.out.restaurant.review;

import kyonggiyo.domain.review.Review;

public interface SaveReviewPort {

    Review save(Review review);

}
