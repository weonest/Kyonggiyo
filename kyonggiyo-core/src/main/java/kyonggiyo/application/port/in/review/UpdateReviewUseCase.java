package kyonggiyo.application.port.in.review;

import kyonggiyo.application.port.in.review.dto.ReviewUpdateCommand;
import kyonggiyo.global.auth.UserInfo;

public interface UpdateReviewUseCase {

    void updateReview(UserInfo userInfo,
                      Long id,
                      ReviewUpdateCommand command);

}
