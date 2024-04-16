package kyonggiyo.application.port.in.review;

import kyonggiyo.application.port.in.auth.dto.UserInfo;
import kyonggiyo.application.port.in.review.dto.ReviewUpdateCommand;

public interface UpdateReviewUseCase {

    void updateReview(UserInfo userInfo,
                      Long id,
                      ReviewUpdateCommand command);

}
