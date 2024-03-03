package kyonggiyo.application.service.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewCreateRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewUpdateRequest;
import kyonggiyo.application.port.in.restaurant.review.CreateReviewUseCase;
import kyonggiyo.application.port.in.restaurant.review.DeleteReviewUseCase;
import kyonggiyo.application.port.in.restaurant.review.UpdateReviewUseCase;
import kyonggiyo.application.port.out.restaurant.GetRestaurantPort;
import kyonggiyo.application.port.out.restaurant.review.DeleteReviewPort;
import kyonggiyo.application.port.out.restaurant.review.GetReviewPort;
import kyonggiyo.application.port.out.restaurant.review.SaveReviewPort;
import kyonggiyo.application.port.out.user.GetUserPort;
import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.Review;
import kyonggiyo.domain.user.User;
import kyonggiyo.global.auth.UserInfo;
import kyonggiyo.global.exception.ForbiddenException;
import kyonggiyo.global.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCommandService implements CreateReviewUseCase, UpdateReviewUseCase, DeleteReviewUseCase {

    private final GetUserPort getUserPort;
    private final GetRestaurantPort getRestaurantPort;
    private final GetReviewPort getReviewPort;
    private final SaveReviewPort saveReviewPort;
    private final DeleteReviewPort deleteReviewPort;

    @Override
    public void createReview(UserInfo userInfo, Long restaurantId, ReviewCreateRequest request) {
        User user = getUserPort.getById(userInfo.userId());
        Restaurant restaurant = getRestaurantPort.getById(restaurantId);
        Review review = Review.builder()
                .rating(request.rating())
                .content(request.content())
                .restaurant(restaurant)
                .reviewer(user)
                .build();
        restaurant.updateAverageRating();
        saveReviewPort.save(review);
    }

    @Override
    public void updateReview(UserInfo userInfo, Long id, ReviewUpdateRequest request) {
        Review review = getReviewPort.getById(id);
        User reviewer = review.getReviewer();
        if (reviewer.getId().equals(userInfo.userId())) {
            review.update(request.rating(), request.content());
            review.getRestaurant().updateAverageRating();
            return;
        }
        throw new ForbiddenException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION,
                String.format("유저 식별자 불일치 -> %d", userInfo.userId()));
    }

    @Override
    public void deleteReview(UserInfo userInfo, Long id) {
        Review review = getReviewPort.getById(id);
        User reviewer = review.getReviewer();
        if (reviewer.getId().equals(userInfo.userId())) {
            deleteReviewPort.deleteById(id);
            return;
        }
        throw new ForbiddenException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION,
                String.format("유저 식별자 불일치 -> %d", userInfo.userId()));
    }

}
