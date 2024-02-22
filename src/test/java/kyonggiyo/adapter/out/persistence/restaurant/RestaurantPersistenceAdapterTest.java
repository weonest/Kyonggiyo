package kyonggiyo.adapter.out.persistence.restaurant;

import kyonggiyo.adapter.out.persistence.AdapterTest;
import kyonggiyo.domain.restaurant.Restaurant;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantPersistenceAdapterTest extends AdapterTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantPersistenceAdapter restaurantPersistenceAdapter;

    @Test
    void 식별자를_통해_DB에서_Restaurant을_조회할_수_있다() {
        // given
        Restaurant restaurant = Instancio.create(Restaurant.class);
        restaurantRepository.save(restaurant);


        entityManager.flush();
        entityManager.clear();

        // when
        Restaurant result = restaurantPersistenceAdapter.getById(restaurant.getId());

        // then
        assertThat(result).isEqualTo(result);
    }

}
