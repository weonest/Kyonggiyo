package kyonggiyo.adapter.in.web.restaurant.dto.review;

import kyonggiyo.adapter.in.web.image.dto.ImageResponse;
import kyonggiyo.adapter.in.web.user.dto.UserResponse;
import kyonggiyo.domain.image.Image;
import kyonggiyo.domain.restaurant.Review;

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

    public static ReviewResponse of(Review review, List<Image> images) {
        return new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt(),
                new UserResponse(review.getReviewerId(),
                        review.getReviewerNickname()),
                images.stream()
                        .map(ImageResponse::from)
                        .toList()
        );
    }

}
