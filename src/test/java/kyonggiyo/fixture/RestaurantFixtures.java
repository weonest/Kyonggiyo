package kyonggiyo.fixture;


import kyonggiyo.domain.restaurant.Restaurant;
import org.instancio.Instancio;

import static org.instancio.Select.field;

public class RestaurantFixtures {

    private RestaurantFixtures() {
    }

    public static Restaurant generateRestaurantEntityWithoutReview() {
        return Instancio.of(Restaurant.class)
                .ignore(field(Restaurant::getReviews))
                .create();
    }

}
