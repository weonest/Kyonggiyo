package kyonggiyo.persistence.restaurant.review;

import kyonggiyo.review.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
}
