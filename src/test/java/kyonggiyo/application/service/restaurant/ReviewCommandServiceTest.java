package kyonggiyo.application.service.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewCreateRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewUpdateRequest;
import kyonggiyo.application.port.out.restaurant.GetRestaurantPort;
import kyonggiyo.application.port.out.restaurant.review.DeleteReviewPort;
import kyonggiyo.application.port.out.restaurant.review.GetReviewPort;
import kyonggiyo.application.port.out.restaurant.review.SaveReviewPort;
import kyonggiyo.application.port.out.user.GetUserPort;
import kyonggiyo.application.service.ServiceTest;
import kyonggiyo.application.service.image.ImageService;
import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.Review;
import kyonggiyo.domain.user.User;
import kyonggiyo.fixture.RestaurantFixtures;
import kyonggiyo.fixture.UserFixtures;
import kyonggiyo.global.auth.UserInfo;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = ReviewCommandService.class)
class ReviewCommandServiceTest extends ServiceTest {

    @Autowired
    private ReviewCommandService reviewCommandService;

    @MockBean
    private GetUserPort getUserPort;
    @MockBean
    private GetRestaurantPort getRestaurantPort;
    @MockBean
    private GetReviewPort getReviewPort;
    @MockBean
    private SaveReviewPort saveReviewPort;
    @MockBean
    private DeleteReviewPort deleteReviewPort;
    @MockBean
    private ImageService imageService;

    @Test
    void 유저의_요청을_통해_리뷰를_생성한다() {
        // given
        User reviewer = UserFixtures.generateUserEntity();
        UserInfo userInfo = new UserInfo(reviewer.getId(), reviewer.getRole());
        Restaurant restaurant = RestaurantFixtures.generateRestaurantEntityWithoutReview();
        ReviewCreateRequest request = Instancio.create(ReviewCreateRequest.class);

        Review review = Review.builder()
                .rating(request.rating())
                .content(request.content())
                .restaurant(restaurant)
                .reviewerId(reviewer.getId())
                .reviewerNickname(reviewer.getNickname())
                .build();

        given(getUserPort.getById(userInfo.userId())).willReturn(reviewer);
        given(getRestaurantPort.getById(restaurant.getId())).willReturn(restaurant);
        given(saveReviewPort.save(review)).willReturn(review);

        // when
        reviewCommandService.createReview(userInfo, restaurant.getId(), request, null);

        // then
        verify(getUserPort, only()).getById(userInfo.userId());
        verify(getRestaurantPort, only()).getById(restaurant.getId());
        verify(saveReviewPort, only()).save(review);
    }

    @Test
    void 작성자의_요청을_통해_리뷰를_수정한다() {
        // given
        User reviewer = UserFixtures.generateUserEntity();
        UserInfo userInfo = new UserInfo(reviewer.getId(), reviewer.getRole());
        Restaurant restaurant = RestaurantFixtures.generateRestaurantEntityWithoutReview();
        ReviewUpdateRequest request = Instancio.create(ReviewUpdateRequest.class);

        Review review = Review.builder()
                .rating(request.rating())
                .content(request.content())
                .restaurant(restaurant)
                .reviewerId(reviewer.getId())
                .reviewerNickname(reviewer.getNickname())
                .build();

        given(getReviewPort.getById(any())).willReturn(review);

        // when
        reviewCommandService.updateReview(userInfo, restaurant.getId(), request, null);

        // then
        verify(getReviewPort, only()).getById(any());
    }

    @Test
    void 작성자의_요청을_통해_리뷰를_삭제한다() {
        // given
        User reviewer = UserFixtures.generateUserEntity();
        UserInfo userInfo = new UserInfo(reviewer.getId(), reviewer.getRole());
        Restaurant restaurant = RestaurantFixtures.generateRestaurantEntityWithoutReview();
        ReviewUpdateRequest request = Instancio.create(ReviewUpdateRequest.class);

        Review review = Review.builder()
                .rating(request.rating())
                .content(request.content())
                .restaurant(restaurant)
                .reviewerId(reviewer.getId())
                .reviewerNickname(reviewer.getNickname())
                .build();

        given(getReviewPort.getById(any())).willReturn(review);

        // when
        reviewCommandService.deleteReview(userInfo, review.getId());

        // then
        verify(getReviewPort, only()).getById(any());
    }

}