package kyonggiyo.adapter.in.web.restaurant.dto;

import kyonggiyo.domain.restaurant.Review;
import kyonggiyo.domain.user.User;

public record ReviewResponse(
        Long id,
        int rating,
        String content,
        User reviewer
) {

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getContent(),
                review.getReviewer()
        );
    }

}
