package kyonggiyo.application.port.out.restaurant;

import kyonggiyo.domain.restaurant.Restaurant;

public interface GetRestaurantPort {

    Restaurant getById(Long id);

}
