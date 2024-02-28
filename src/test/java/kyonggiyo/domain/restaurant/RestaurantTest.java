package kyonggiyo.domain.restaurant;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

class RestaurantTest {

    @Test
    void 리뷰들의_평점을_통해_식당_평균평점을_구할_수_있다() {
        List<Review> reviews = Instancio.ofList(Review.class)
                .size(20)
                .supply(field(Review::getRating), gen -> gen.intRange(1, 5))
                .create();
        TreeSet<Review> reviews1 = new TreeSet<>(reviews);

        Restaurant restaurant = Instancio.of(Restaurant.class)
                .ignore(field(Restaurant::getAverageRating))
                .set(field(Restaurant::getReviews), reviews1)
                .create();

        restaurant.updateAverageRating();

        assertThat(restaurant.getAverageRating()).isBetween(1.0f, 5.0f);
    }

}
