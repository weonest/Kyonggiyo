package kyonggiyo.application.port.out.restaurant;

import kyonggiyo.domain.restaurant.Review;

public interface SaveReviewPort {

    Review save(Review review);

}
