package kyonggiyo.adapter.out.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kyonggiyo.adapter.out.persistence.account.AccountJpaRepository;
import kyonggiyo.adapter.out.persistence.account.AccountPersistenceAdapter;
import kyonggiyo.adapter.out.persistence.account.JpaAccountRepositoryImpl;
import kyonggiyo.adapter.out.persistence.restaurant.JpaRestaurantRepositoryImpl;
import kyonggiyo.adapter.out.persistence.restaurant.RestaurantJpaRepository;
import kyonggiyo.adapter.out.persistence.restaurant.RestaurantPersistenceAdapter;
import kyonggiyo.global.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({AdapterTest.AdapterTestConfig.class, JpaAuditingConfig.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public abstract class AdapterTest {

    @PersistenceContext
    protected EntityManager entityManager;

    @TestConfiguration
    public static class AdapterTestConfig {

        @Bean
        public JpaAccountRepositoryImpl jpaAccountRepositoryImpl(AccountJpaRepository accountJpaRepository) {
            return new JpaAccountRepositoryImpl(accountJpaRepository);
        }

        @Bean
        public AccountPersistenceAdapter accountPersistenceAdapter(JpaAccountRepositoryImpl jpaAccountRepository) {
            return new AccountPersistenceAdapter(jpaAccountRepository);
        }

        @Bean
        public JpaRestaurantRepositoryImpl jpaRestaurantRepositoryImpl(RestaurantJpaRepository restaurantJpaRepository) {
            return new JpaRestaurantRepositoryImpl(restaurantJpaRepository);
        }

        @Bean
        public RestaurantPersistenceAdapter restaurantPersistenceAdapter(JpaRestaurantRepositoryImpl jpaRestaurantRepository) {
            return new RestaurantPersistenceAdapter(jpaRestaurantRepository);
        }

    }

}
