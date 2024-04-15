package kyonggiyo.application.port.in.restaurant.review;

import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewCreateRequest;
import kyonggiyo.global.auth.UserInfo;

public interface CreateReviewUseCase {

    void createReview(UserInfo userInfo,
                      Long restaurantId,
                      ReviewCreateRequest request);

}
