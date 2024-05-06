package kyonggiyo.restaurant.port.outbound;

import kyonggiyo.restaurant.domain.entity.Restaurant;
import kyonggiyo.restaurant.domain.vo.RestaurantCategory;

import java.util.List;

public interface LoadRestaurantPort {

    Restaurant getById(Long id);

    List<Restaurant> findAll();

    List<Restaurant> findByNameOrReviewContent(String keyword);

    List<Restaurant> filterByCategory(List<RestaurantCategory> categories);

}
