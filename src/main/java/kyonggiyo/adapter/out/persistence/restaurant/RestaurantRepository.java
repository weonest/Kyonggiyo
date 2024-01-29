package kyonggiyo.adapter.out.persistence.restaurant;

import kyonggiyo.domain.restaurant.Restaurant;

public interface RestaurantRepository {

    Restaurant getById(Long id);

    Restaurant save(Restaurant restaurant);

}
