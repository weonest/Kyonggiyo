package kyonggiyo.persistence.restaurant.review;

import kyonggiyo.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
}
