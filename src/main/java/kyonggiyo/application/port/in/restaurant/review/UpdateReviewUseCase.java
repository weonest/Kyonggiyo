package kyonggiyo.application.port.in.restaurant.review;

import kyonggiyo.adapter.in.web.restaurant.dto.ReviewUpdateRequest;
import kyonggiyo.global.auth.UserInfo;

public interface UpdateReviewUseCase {

    public void updateReview(UserInfo userInfo, Long id, ReviewUpdateRequest request);

}
