package kyonggiyo.persistence.restaurant;

import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.RestaurantCategory;
import kyonggiyo.global.exception.GlobalErrorCode;
import kyonggiyo.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaRestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository jpaRepository;

    @Override
    public Restaurant getById(Long id) {
        Restaurant restaurant = jpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(GlobalErrorCode.NOT_FOUND_ENTITY_EXCEPTION));
        return restaurant;
    }

    @Override
    public List<Restaurant> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<Restaurant> findByNameOrReviewContent(String keyword) {
        return jpaRepository.findByNameOrReviewContent(keyword);
    }

    @Override
    public List<Restaurant> filterByCategory(List<RestaurantCategory> categories) {
        return jpaRepository.filterByCategory(categories);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return jpaRepository.save(restaurant);
    }
    
}
