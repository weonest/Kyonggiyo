package kyonggiyo.application.port.in.restaurant.review;

import kyonggiyo.global.auth.UserInfo;

public interface DeleteReviewUseCase {

    void deleteReview(UserInfo userInfo, Long id);

}
