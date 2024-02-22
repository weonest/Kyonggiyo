package kyonggiyo.adapter.out.persistence.restaurant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({RestaurantPersistenceAdapter.class, JpaRestaurantRepositoryImpl.class})
class RestaurantPersistenceAdapterTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RestaurantPersistenceAdapter restaurantPersistenceAdapter;

    @Test
    @DisplayName("식별자를 통해 DB에서 식당을 조회할 수 있다.")
    void getRestaurantTest() {

        restaurantPersistenceAdapter.getById(1L);
    }

}
