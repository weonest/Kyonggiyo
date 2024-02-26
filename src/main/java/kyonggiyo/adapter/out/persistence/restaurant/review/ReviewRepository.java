package kyonggiyo.adapter.out.persistence.restaurant.review;

import kyonggiyo.domain.restaurant.Review;

public interface ReviewRepository {

    Review getById(Long id);

    Review save(Review review);

}
