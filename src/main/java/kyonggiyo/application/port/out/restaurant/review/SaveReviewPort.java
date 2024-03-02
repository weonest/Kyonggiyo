package kyonggiyo.application.port.out.restaurant.review;

import kyonggiyo.domain.restaurant.Review;

public interface SaveReviewPort {

    Review save(Review review);

}
