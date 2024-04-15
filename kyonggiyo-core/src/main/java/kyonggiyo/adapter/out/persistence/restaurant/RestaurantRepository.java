package kyonggiyo.adapter.out.persistence.restaurant;

import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.RestaurantCategory;

import java.util.List;

public interface RestaurantRepository {

    Restaurant getById(Long id);

    List<Restaurant> findAll();

    List<Restaurant> findByNameOrReviewContent(String keyword);

    List<Restaurant> filterByCategory(List<RestaurantCategory> categories);

    Restaurant save(Restaurant restaurant);

}
