package kyonggiyo.application.port.in.review;

import kyonggiyo.application.port.in.auth.dto.UserInfo;
import kyonggiyo.application.port.in.review.dto.ReviewCreateCommand;

public interface CreateReviewUseCase {

    void createReview(UserInfo userInfo,
                      Long restaurantId,
                      ReviewCreateCommand command);

}
