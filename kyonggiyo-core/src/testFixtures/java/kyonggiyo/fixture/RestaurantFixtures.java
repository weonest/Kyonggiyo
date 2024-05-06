package kyonggiyo.fixture;


import kyonggiyo.restaurant.domain.entity.Restaurant;
import kyonggiyo.restaurant.domain.vo.RestaurantCategory;
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
                .ignore(field(Restaurant::getAverageRating))
                .create();
    }

    public static Restaurant generateRestaurantEntityWithoutReview() {
        return Instancio.of(Restaurant.class)
                .ignore(field(Restaurant::getReviews))
                .ignore(field(Restaurant::getAverageRating))
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
