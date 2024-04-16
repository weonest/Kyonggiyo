package adapter.out.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kyonggiyo.adapter.out.persistence.account.AccountJpaRepository;
import kyonggiyo.adapter.out.persistence.account.AccountPersistenceAdapter;
import kyonggiyo.adapter.out.persistence.account.JpaAccountRepositoryImpl;
import kyonggiyo.adapter.out.persistence.candidate.CandidateJpaRepository;
import kyonggiyo.adapter.out.persistence.candidate.CandidatePersistenceAdapter;
import kyonggiyo.adapter.out.persistence.candidate.JpaCandidateRepositoryImpl;
import kyonggiyo.adapter.out.persistence.event.EventJpaRepository;
import kyonggiyo.adapter.out.persistence.event.EventPersistenceAdapter;
import kyonggiyo.adapter.out.persistence.event.JpaEventRepositoryImpl;
import kyonggiyo.adapter.out.persistence.restaurant.JpaRestaurantRepositoryImpl;
import kyonggiyo.adapter.out.persistence.restaurant.RestaurantJpaRepository;
import kyonggiyo.adapter.out.persistence.restaurant.RestaurantPersistenceAdapter;
import kyonggiyo.adapter.out.persistence.restaurant.review.JpaReviewRepositoryImpl;
import kyonggiyo.adapter.out.persistence.restaurant.review.ReviewJpaRepository;
import kyonggiyo.adapter.out.persistence.restaurant.review.ReviewPersistenceAdapter;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(AdapterTest.AdapterTestConfig.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public abstract class AdapterTest {

    @PersistenceContext
    protected EntityManager entityManager;

    @TestConfiguration
    public static class AdapterTestConfig {

        // account
        @Bean
        public JpaAccountRepositoryImpl jpaAccountRepositoryImpl(AccountJpaRepository accountJpaRepository) {
            return new JpaAccountRepositoryImpl(accountJpaRepository);
        }

        @Bean
        public AccountPersistenceAdapter accountPersistenceAdapter(JpaAccountRepositoryImpl jpaAccountRepository) {
            return new AccountPersistenceAdapter(jpaAccountRepository);
        }

        // restaurant
        @Bean
        public JpaRestaurantRepositoryImpl jpaRestaurantRepositoryImpl(RestaurantJpaRepository restaurantJpaRepository) {
            return new JpaRestaurantRepositoryImpl(restaurantJpaRepository);
        }

        @Bean
        public RestaurantPersistenceAdapter restaurantPersistenceAdapter(JpaRestaurantRepositoryImpl jpaRestaurantRepository) {
            return new RestaurantPersistenceAdapter(jpaRestaurantRepository);
        }

        // review
        @Bean
        public JpaReviewRepositoryImpl jpaReviewRepositoryImpl(ReviewJpaRepository reviewJpaRepository) {
            return new JpaReviewRepositoryImpl(reviewJpaRepository);
        }

        @Bean
        public ReviewPersistenceAdapter reviewPersistenceAdapter(JpaReviewRepositoryImpl jpaReviewRepository) {
            return new ReviewPersistenceAdapter(jpaReviewRepository);
        }

        // candidate
        @Bean
        public JpaCandidateRepositoryImpl jpaCandidateRepositoryImpl(CandidateJpaRepository candidateJpaRepository) {
            return new JpaCandidateRepositoryImpl(candidateJpaRepository);
        }

        @Bean
        public CandidatePersistenceAdapter candidatePersistenceAdapter(JpaCandidateRepositoryImpl jpaCandidateRepository) {
            return new CandidatePersistenceAdapter(jpaCandidateRepository);
        }

        // evnet
        @Bean
        public JpaEventRepositoryImpl jpaEventRepositoryImpl(EventJpaRepository eventJpaRepository) {
            return new JpaEventRepositoryImpl(eventJpaRepository);
        }

        @Bean
        public EventPersistenceAdapter eventPersistenceAdapter(JpaEventRepositoryImpl jpaEventRepository) {
            return new EventPersistenceAdapter(jpaEventRepository);
        }

    }

}
