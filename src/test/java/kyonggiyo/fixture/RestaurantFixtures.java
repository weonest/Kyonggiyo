package kyonggiyo.fixture;


import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.RestaurantCategory;
import org.instancio.Instancio;

import java.util.List;

import static org.instancio.Select.field;

public class RestaurantFixtures {

    private RestaurantFixtures() {
    }

    public static Restaurant generateRestaurantDomainWithoutReview() {
        return Instancio.of(Restaurant.class)
                .ignore(field(Restaurant::getId))
                .ignore(field(Restaurant::getReviews))
                .create();
    }

    public static List<Restaurant> generateRestaurantEntityList(String keyword, int targetSize) {
        return Instancio.ofList(Restaurant.class)
                .size(targetSize)
                .generate(field(Restaurant::getName), gen -> gen.string().prefix(keyword))
                .create();
    }

    public static List<Restaurant> generateRestaurantEntityList(RestaurantCategory category, int targetSize) {
        return Instancio.ofList(Restaurant.class)
                .size(targetSize)
                .set(field(Restaurant::getCategory), category)
                .create();
    }

}
