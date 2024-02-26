package kyonggiyo.adapter.out.persistence.restaurant.review;

import kyonggiyo.domain.restaurant.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
}
