package kyonggiyo.application.port.in.review;

import kyonggiyo.application.port.in.review.dto.ReviewCreateCommand;
import kyonggiyo.global.auth.UserInfo;

public interface CreateReviewUseCase {

    void createReview(UserInfo userInfo,
                      Long restaurantId,
                      ReviewCreateCommand command);

}
