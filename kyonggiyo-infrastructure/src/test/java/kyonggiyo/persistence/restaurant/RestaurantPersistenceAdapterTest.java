package kyonggiyo.persistence.restaurant;

import kyonggiyo.persistence.AdapterTest;
import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.review.domain.entity.Review;
import kyonggiyo.fixture.RestaurantFixtures;
import kyonggiyo.fixture.ReviewFixtures;
import kyonggiyo.persistence.restaurant.review.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantPersistenceAdapterTest extends AdapterTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantPersistenceAdapter restaurantPersistenceAdapter;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 식별자를_통해_DB에서_Restaurant을_조회할_수_있다() {
        // given
        Restaurant restaurant = RestaurantFixtures.generateRestaurantDomainWithoutReview();
        Restaurant expect = restaurantRepository.save(restaurant);

        entityManager.flush();
        entityManager.clear();

        // when
        Restaurant result = restaurantPersistenceAdapter.getById(expect.getId());

        // then
        assertThat(result).isEqualTo(restaurant);
    }

    @Test
    void DB에서_전체_Restaurant을_조회할_수_있다() {
        // given
        int expectSize = 2;
        Restaurant restaurant = RestaurantFixtures.generateRestaurantDomainWithoutReview();
        Restaurant restaurant2 = RestaurantFixtures.generateRestaurantDomainWithoutReview();
        restaurantRepository.save(restaurant);
        restaurantRepository.save(restaurant2);

        entityManager.flush();
        entityManager.clear();

        // when
        List<Restaurant> result = restaurantPersistenceAdapter.findAll();

        // then
        assertThat(result).hasSize(expectSize);
    }

    @Test
    void DB에서_Restaurant을_이름_또는_리뷰내용으로_조회할_수_있다() {
        // given
        Restaurant restaurant = RestaurantFixtures.generateRestaurantDomainWithoutReview();
        Review review = ReviewFixtures.createReviewDomain(restaurant);
        restaurantRepository.save(restaurant);
        reviewRepository.save(review);

        entityManager.flush();
        entityManager.clear();

        // when
        List<Restaurant> resultByName = restaurantPersistenceAdapter.findByNameOrReviewContent(restaurant.getName());
        List<Restaurant> resultByReviewContent = restaurantPersistenceAdapter.findByNameOrReviewContent("떡볶이");

        // then
        assertThat(resultByName.get(0)).isEqualTo(restaurant);
        assertThat(resultByReviewContent.get(0)).isEqualTo(restaurant);
    }

    @Test
    void DB에서_Restaurant을_카테고리로_조회할_수_있다() {
        // given
        Restaurant restaurant = RestaurantFixtures.generateRestaurantDomainWithoutReview();
        restaurantRepository.save(restaurant);

        entityManager.flush();
        entityManager.clear();

        // when
        List<Restaurant> resultByName = restaurantPersistenceAdapter.filterByCategory(List.of(restaurant.getCategory()));

        // then
        assertThat(resultByName.get(0)).isEqualTo(restaurant);
    }

}
