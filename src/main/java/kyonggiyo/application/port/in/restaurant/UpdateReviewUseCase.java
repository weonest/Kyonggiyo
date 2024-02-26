package kyonggiyo.application.port.in.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.ReviewUpdateRequest;

public interface UpdateReviewUseCase {

    public void update(Long reviewId, ReviewUpdateRequest request);

}
