package kyonggiyo.review.port.inbound;

import kyonggiyo.auth.domain.vo.UserInfo;
import kyonggiyo.review.dto.ReviewUpdateCommand;

public interface UpdateReviewUseCase {

    void updateReview(UserInfo userInfo,
                      Long id,
                      ReviewUpdateCommand command);

}
