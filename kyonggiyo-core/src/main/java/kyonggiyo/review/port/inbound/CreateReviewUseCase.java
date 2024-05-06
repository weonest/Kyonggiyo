package kyonggiyo.review.port.inbound;

import kyonggiyo.auth.domain.vo.UserInfo;
import kyonggiyo.review.dto.ReviewCreateCommand;

public interface CreateReviewUseCase {

    void createReview(UserInfo userInfo,
                      Long restaurantId,
                      ReviewCreateCommand command);

}
