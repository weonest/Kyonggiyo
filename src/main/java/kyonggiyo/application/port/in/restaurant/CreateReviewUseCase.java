package kyonggiyo.application.port.in.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.ReviewCreateRequest;
import kyonggiyo.global.auth.UserInfo;

public interface CreateReviewUseCase {

    void create(UserInfo userInfo, Long restaurantId, ReviewCreateRequest request);

}
