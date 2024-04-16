package kyonggiyo.application.port.out.restaurant.review;

import kyonggiyo.domain.review.Review;

public interface LoadReviewPort {

    Review getById(Long id);

}
