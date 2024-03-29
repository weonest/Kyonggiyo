package kyonggiyo.application.port.in.restaurant.review;

import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewUpdateRequest;
import kyonggiyo.global.auth.UserInfo;

public interface UpdateReviewUseCase {

    void updateReview(UserInfo userInfo,
                      Long id,
                      ReviewUpdateRequest request);

}
