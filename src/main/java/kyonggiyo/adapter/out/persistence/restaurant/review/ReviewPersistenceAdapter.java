package kyonggiyo.adapter.out.persistence.restaurant.review;

import kyonggiyo.application.port.out.restaurant.GetReviewPort;
import kyonggiyo.application.port.out.restaurant.SaveReviewPort;
import kyonggiyo.domain.restaurant.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements SaveReviewPort, GetReviewPort {

    private ReviewRepository reviewRepository;

    @Override
    public Review getById(Long id) {
        return reviewRepository.getById(id);
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

}
