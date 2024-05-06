package kyonggiyo.application.service.review;

import com.github.f4b6a3.tsid.TsidCreator;
import jakarta.persistence.EntityManager;
import kyonggiyo.auth.domain.vo.UserInfo;
import kyonggiyo.application.port.in.review.CreateReviewUseCase;
import kyonggiyo.application.port.in.review.DeleteReviewUseCase;
import kyonggiyo.application.port.in.review.UpdateReviewUseCase;
import kyonggiyo.application.port.in.review.dto.ReviewCreateCommand;
import kyonggiyo.application.port.in.review.dto.ReviewUpdateCommand;
import kyonggiyo.application.port.out.restaurant.LoadRestaurantPort;
import kyonggiyo.application.port.out.restaurant.review.DeleteReviewPort;
import kyonggiyo.application.port.out.restaurant.review.LoadReviewPort;
import kyonggiyo.application.port.out.restaurant.review.SaveReviewPort;
import kyonggiyo.user.port.outbound.LoadUserPort;
import kyonggiyo.application.service.event.review.ReviewCreateEvent;
import kyonggiyo.image.service.ImageService;
import kyonggiyo.image.domain.vo.ImageType;
import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.review.Review;
import kyonggiyo.user.domain.entity.User;
import kyonggiyo.common.exception.ForbiddenException;
import kyonggiyo.common.exception.GlobalErrorCode;
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
    private final EntityManager entityManager;

    @Override
    public void createReview(UserInfo userInfo,
                             Long restaurantId,
                             ReviewCreateCommand command) {
        User user = loadUserPort.getById(userInfo.userId());
        Restaurant restaurant = loadRestaurantPort.getById(restaurantId);
        Review review = Review.builder()
                .rating(command.rating())
                .content(command.content())
                .restaurant(restaurant)
                .reviewerId(user.getId())
                .reviewerNickname(user.getNickname())
                .build();

        updateRestaurantWithoutDirtyChecking(restaurant);
        Review savedReview = saveReviewPort.save(review);

        if (Objects.isNull(command.imageUrls()) || command.imageUrls().isEmpty())
            return;

        Long eventId = TsidCreator.getTsid().toLong();
        ReviewCreateEvent reviewCreateEvent = ReviewCreateEvent.of(eventId, command.imageUrls(), ImageType.REVIEW, savedReview.getId());
        eventPublisher.publishEvent(reviewCreateEvent);
    }
    
    @Override
    public void updateReview(UserInfo userInfo,
                             Long id,
                             ReviewUpdateCommand command) {
        Review review = loadReviewPort.getById(id);

        validateUser(userInfo.userId(), review.getReviewerId());

        review.update(command.rating(), command.content());
        review.getRestaurant().updateAverageRating();

        if (Objects.isNull(command.imageUrls()) || command.imageUrls().isEmpty())
            return;

        Long eventId = TsidCreator.getTsid().toLong();
        ReviewCreateEvent imageCreateEvent = ReviewCreateEvent.of(eventId, command.imageUrls(), ImageType.REVIEW, review.getId());
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

    private void updateRestaurantWithoutDirtyChecking(Restaurant restaurant) {
        restaurant.updateAverageRating();
        entityManager.flush();
    }

    private void validateUser(Long userId, Long reviewerId) {
        if (!userId.equals(reviewerId))
            throw new ForbiddenException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION,
                    String.format("유저 식별자 불일치 -> %d", userId));
    }

}
