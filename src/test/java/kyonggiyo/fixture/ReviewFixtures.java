package kyonggiyo.fixture;

import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.Review;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

public class ReviewFixtures {
    
    private ReviewFixtures() {
    }
    
    public static Review createReviewDomain(Restaurant restaurant) {
        Review review = Review.builder()
                .rating(5)
                .content("떡볶이가 맛있다.")
                .restaurant(restaurant)
                .reviewerId(1L)
                .reviewerNickname("유저")
                .build();
        ReflectionTestUtils.setField(review, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(review, "updatedAt", LocalDateTime.now());
        return review;
    }
    
}
