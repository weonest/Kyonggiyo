package kyonggiyo.application.port.out.restaurant.review;

import kyonggiyo.domain.restaurant.Review;

public interface LoadReviewPort {

    Review getById(Long id);

}
