package kyonggiyo.application.port.out.restaurant;

import kyonggiyo.domain.restaurant.Review;

public interface GetReviewPort {

    Review getById(Long id);

}
