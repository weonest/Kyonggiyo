package kyonggiyo.review.port.outbound;

import kyonggiyo.review.domain.entity.Review;

public interface LoadReviewPort {

    Review getById(Long id);

}
