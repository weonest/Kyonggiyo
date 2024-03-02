package kyonggiyo.adapter.in.web.restaurant.dto;

import kyonggiyo.adapter.in.web.user.dto.UserResponse;
import kyonggiyo.domain.restaurant.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        int rating,
        String content,
        LocalDateTime createdAt,
        UserResponse reviewer
) {

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt(),
                new UserResponse(review.getReviewer().getId(),
                        review.getReviewer().getNickname())
        );
    }

}
