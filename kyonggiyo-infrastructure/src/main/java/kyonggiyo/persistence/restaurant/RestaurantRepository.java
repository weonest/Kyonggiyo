package kyonggiyo.persistence.restaurant;

import kyonggiyo.restaurant.domain.entity.Restaurant;
import kyonggiyo.restaurant.domain.vo.RestaurantCategory;

import java.util.List;

public interface RestaurantRepository {

    Restaurant getById(Long id);

    List<Restaurant> findAll();

    List<Restaurant> findByNameOrReviewContent(String keyword);

    List<Restaurant> filterByCategory(List<RestaurantCategory> categories);

    Restaurant save(Restaurant restaurant);

}
