package kyonggiyo.application.port.out.restaurant;

import kyonggiyo.domain.restaurant.Restaurant;

import java.util.List;

public interface GetRestaurantPort {

    Restaurant getById(Long id);

    List<Restaurant> getAllRestaurants();

}
