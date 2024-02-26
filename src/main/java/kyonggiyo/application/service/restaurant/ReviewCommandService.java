package kyonggiyo.application.service.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.ReviewCreateRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.ReviewUpdateRequest;
import kyonggiyo.application.port.in.restaurant.CreateReviewUseCase;
import kyonggiyo.application.port.in.restaurant.UpdateReviewUseCase;
import kyonggiyo.application.port.out.restaurant.GetRestaurantPort;
import kyonggiyo.application.port.out.restaurant.GetReviewPort;
import kyonggiyo.application.port.out.restaurant.SaveReviewPort;
import kyonggiyo.application.port.out.user.GetUserPort;
import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.Review;
import kyonggiyo.domain.user.User;
import kyonggiyo.global.auth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCommandService implements CreateReviewUseCase, UpdateReviewUseCase {

    private GetUserPort getUserPort;
    private GetRestaurantPort getRestaurantPort;
    private GetReviewPort getReviewPort;
    private SaveReviewPort saveReviewPort;

    public void create(UserInfo userInfo, Long restaurantId, ReviewCreateRequest request) {
        User user = getUserPort.getById(userInfo.userId());
        Restaurant restaurant = getRestaurantPort.getById(restaurantId);
        Review review = Review.builder()
                .rating(request.rating())
                .content(request.content())
                .restaurant(restaurant)
                .reviewer(user)
                .build();
        saveReviewPort.save(review);
    }

    public void update(Long reviewId, ReviewUpdateRequest request) {
        Review review = getReviewPort.getById(reviewId);
        review.update(request.rating(), request.content());
        review.getRestaurant().updateAverageRating();
    }

}
