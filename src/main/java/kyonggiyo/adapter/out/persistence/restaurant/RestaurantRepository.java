package kyonggiyo.adapter.out.persistence.restaurant;

import kyonggiyo.domain.restaurant.Restaurant;

public interface RestaurantRepository {

    Restaurant get(Long id);

    void save(Restaurant restaurant);

}
