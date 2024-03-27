package kyonggiyo.application.service.restaurant;

import com.github.f4b6a3.tsid.TsidCreator;
import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewCreateRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewUpdateRequest;
import kyonggiyo.application.port.in.restaurant.review.CreateReviewUseCase;
import kyonggiyo.application.port.in.restaurant.review.DeleteReviewUseCase;
import kyonggiyo.application.port.in.restaurant.review.UpdateReviewUseCase;
import kyonggiyo.application.port.out.restaurant.LoadRestaurantPort;
import kyonggiyo.application.port.out.restaurant.review.DeleteReviewPort;
import kyonggiyo.application.port.out.restaurant.review.LoadReviewPort;
import kyonggiyo.application.port.out.restaurant.review.SaveReviewPort;
import kyonggiyo.application.port.out.user.LoadUserPort;
import kyonggiyo.application.service.event.ImageCreateEvent;
import kyonggiyo.application.service.image.ImageService;
import kyonggiyo.domain.image.ImageType;
import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.Review;
import kyonggiyo.domain.user.User;
import kyonggiyo.global.auth.UserInfo;
import kyonggiyo.global.exception.ForbiddenException;
import kyonggiyo.global.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCommandService implements CreateReviewUseCase, UpdateReviewUseCase, DeleteReviewUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadRestaurantPort loadRestaurantPort;
    private final LoadReviewPort loadReviewPort;
    private final SaveReviewPort saveReviewPort;
    private final DeleteReviewPort deleteReviewPort;
    private final ApplicationEventPublisher eventPublisher;
    private final ImageService imageService;

    @Override
    public void createReview(UserInfo userInfo,
                             Long restaurantId,
                             ReviewCreateRequest request) {
        User user = loadUserPort.getById(userInfo.userId());
        Restaurant restaurant = loadRestaurantPort.getById(restaurantId);
        Review review = Review.builder()
                .rating(request.rating())
                .content(request.content())
                .restaurant(restaurant)
                .reviewerId(user.getId())
                .reviewerNickname(user.getNickname())
                .build();
        restaurant.updateAverageRating();
        Review savedReview = saveReviewPort.save(review);

        if (Objects.isNull(request.imageUrls()) || request.imageUrls().isEmpty())
            return;

        Long eventId = TsidCreator.getTsid().toLong();
        ImageCreateEvent imageCreateEvent = ImageCreateEvent.of(eventId, request.imageUrls(), ImageType.REVIEW, savedReview.getId());
        eventPublisher.publishEvent(imageCreateEvent);
    }
    
    @Override
    public void updateReview(UserInfo userInfo,
                             Long id,
                             ReviewUpdateRequest request) {
        Review review = loadReviewPort.getById(id);

        validateUser(userInfo.userId(), review.getReviewerId());

        review.update(request.rating(), request.content());
        review.getRestaurant().updateAverageRating();

        if (Objects.isNull(request.imageUrls()) || request.imageUrls().isEmpty())
            return;

        Long eventId = TsidCreator.getTsid().toLong();
        ImageCreateEvent imageCreateEvent = ImageCreateEvent.of(eventId, request.imageUrls(), ImageType.REVIEW, review.getId());
        eventPublisher.publishEvent(imageCreateEvent);
    }

    @Override
    public void deleteReview(UserInfo userInfo, Long id) {
        Review review = loadReviewPort.getById(id);
        validateUser(userInfo.userId(), review.getReviewerId());

        review.deleteReview();

        deleteReviewPort.deleteById(id);
        imageService.deleteByImageTypeAndReferenceId(ImageType.REVIEW, review.getId());
    }

    private void validateUser(Long userId, Long reviewerId) {
        if (!userId.equals(reviewerId))
            throw new ForbiddenException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION,
                    String.format("유저 식별자 불일치 -> %d", userId));
    }

}
