package kyonggiyo.adapter.out.persistence.restaurant;

import kyonggiyo.application.port.out.restaurant.GetRestaurantPort;
import kyonggiyo.application.port.out.restaurant.SaveRestaurantPort;
import kyonggiyo.domain.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantPersistenceAdapter implements GetRestaurantPort, SaveRestaurantPort {

    private final RestaurantRepository repository;

    @Override
    public Restaurant getById(Long id) {
        Restaurant restaurant = repository.getById(id);
        return restaurant;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return repository.getAllRestaurants();
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

}
