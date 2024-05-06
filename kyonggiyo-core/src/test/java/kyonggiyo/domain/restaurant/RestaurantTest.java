package kyonggiyo.domain.restaurant;

import kyonggiyo.restaurant.domain.entity.Restaurant;
import kyonggiyo.review.domain.entity.Review;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

class RestaurantTest {

    @Test
    void 리뷰들의_평점을_통해_식당_평균평점을_구할_수_있다() {
        // given
        Set<Review> reviews = new HashSet<>(
                Instancio.ofList(Review.class)
                .size(20)
                .supply(field(Review::getRating), gen -> gen.intRange(1, 5))
                .create());

        Restaurant restaurant = Instancio.of(Restaurant.class)
                .ignore(field(Restaurant::getAverageRating))
                .set(field(Restaurant::getReviews), reviews)
                .create();

        // when
        restaurant.updateAverageRating();

        // then
        assertThat(restaurant.getAverageRating()).isBetween(1.0f, 5.0f);
    }

}
