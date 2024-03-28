package kyonggiyo.application.service.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewCreateRequest;
import kyonggiyo.adapter.out.persistence.restaurant.RestaurantJpaRepository;
import kyonggiyo.adapter.out.persistence.restaurant.review.ReviewJpaRepository;
import kyonggiyo.adapter.out.persistence.user.UserRepository;
import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.Review;
import kyonggiyo.domain.user.User;
import kyonggiyo.fixture.RestaurantFixtures;
import kyonggiyo.fixture.ReviewFixtures;
import kyonggiyo.global.auth.UserInfo;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ReviewConcurrencyTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewJpaRepository reviewRepository;

    @Autowired
    private RestaurantJpaRepository restaurantRepository;

    @Autowired
    private ReviewCommandService reviewCommandService;

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void 식당_리뷰_생성_동시성_테스트() throws InterruptedException {
        User user = userRepository.save(new User("tester"));
        Restaurant restaurantDomain = RestaurantFixtures.generateRestaurantDomainWithoutReview();
        Review reviewDomain = ReviewFixtures.createReviewDomain(restaurantDomain);

        Restaurant restaurant = restaurantRepository.save(restaurantDomain);
        reviewRepository.save(reviewDomain);

        int count = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        CountDownLatch countDownLatch = new CountDownLatch(count);

        // when
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    int rating = RandomGenerator.getDefault().nextInt(1, 5);
                    reviewCommandService.createReview(new UserInfo(user.getId(), user.getRole()),
                            restaurant.getId(),
                            new ReviewCreateRequest(rating, "good", List.of("url")));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        // then
        List<Review> savedReviews = reviewRepository.findAll();
        assertThat(savedReviews.size() - 1).isEqualTo(count);
    }

}
