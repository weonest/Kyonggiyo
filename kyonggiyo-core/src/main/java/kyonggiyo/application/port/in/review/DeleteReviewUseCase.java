package kyonggiyo.application.port.in.review;

import kyonggiyo.global.auth.UserInfo;

public interface DeleteReviewUseCase {

    void deleteReview(UserInfo userInfo, Long id);

}
