package kyonggiyo.application.port.out.restaurant;

import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.RestaurantCategory;

import java.util.List;

public interface LoadRestaurantPort {

    Restaurant getById(Long id);

    List<Restaurant> findAll();

    List<Restaurant> findByNameOrReviewContent(String keyword);

    List<Restaurant> filterByCategory(List<RestaurantCategory> categories);

}
