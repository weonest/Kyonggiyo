package kyonggiyo.review.dto;

import kyonggiyo.image.dto.ImageResponse;
import kyonggiyo.user.dto.UserResponse;
import kyonggiyo.review.domain.entity.Review;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewResponse(
        Long id,
        int rating,
        String content,
        LocalDateTime createdAt,
        UserResponse reviewer,
        List<ImageResponse> images
) {

    public static ReviewResponse of(Review review, List<ImageResponse> images) {
        return new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt(),
                new UserResponse(review.getReviewerId(),
                        review.getReviewerNickname()),
                images
        );
    }

}
