package kyonggiyo.persistence.restaurant.review;

import kyonggiyo.review.domain.entity.Review;

public interface ReviewRepository {

    Review getById(Long id);

    Review save(Review review);

    void deleteById(Long id);

}
