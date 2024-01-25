package kyonggiyo.adapter.out.persistence.restaurant;

import kyonggiyo.domain.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaRestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository jpaRepository;

    @Override
    public Restaurant getById(Long id) {
        Restaurant restaurant = jpaRepository.findById(id)
                .orElseThrow();
        return restaurant;
    }

    @Override
    public void save(Restaurant restaurant) {
        jpaRepository.save(restaurant);
    }
    
}
