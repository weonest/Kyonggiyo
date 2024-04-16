package kyonggiyo.persistence.restaurant;

import kyonggiyo.application.port.out.restaurant.LoadRestaurantPort;
import kyonggiyo.application.port.out.restaurant.SaveRestaurantPort;
import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.RestaurantCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantPersistenceAdapter implements LoadRestaurantPort, SaveRestaurantPort {

    private final RestaurantRepository repository;

    @Override
    public Restaurant getById(Long id) {
        Restaurant restaurant = repository.getById(id);
        return restaurant;
    }

    @Override
    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Restaurant> findByNameOrReviewContent(String keyword) {
        return repository.findByNameOrReviewContent(keyword);
    }

    @Override
    public List<Restaurant> filterByCategory(List<RestaurantCategory> categories) {
        return repository.filterByCategory(categories);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return repository.save(restaurant);
    }

}
