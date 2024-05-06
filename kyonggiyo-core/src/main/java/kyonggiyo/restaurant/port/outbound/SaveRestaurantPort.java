package kyonggiyo.restaurant.port.outbound;

import kyonggiyo.restaurant.domain.entity.Restaurant;

public interface SaveRestaurantPort {

    Restaurant save(Restaurant restaurant);

}

