package kyonggiyo.adapter.out.persistence.restaurant;

import kyonggiyo.application.port.out.restaurant.GetRestaurantPort;
import kyonggiyo.application.port.out.restaurant.SaveRestaurantPort;
import kyonggiyo.domain.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantPersistenceAdapter implements GetRestaurantPort, SaveRestaurantPort {

    private final RestaurantRepository repository;

    @Override
    public Restaurant get(Long id) {
        Restaurant restaurant = repository.get(id);
        return restaurant;
    }

    @Override
    public void save(Restaurant restaurant) {
        repository.save(restaurant);
    }

}
