package kyonggiyo.adapter.out.persistence.restaurant;

import kyonggiyo.domain.restaurant.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant getById(Long id);

    List<Restaurant> getAllRestaurants();

    Restaurant save(Restaurant restaurant);

}
