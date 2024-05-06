package kyonggiyo.persistence.restaurant.review;

import kyonggiyo.review.port.outbound.DeleteReviewPort;
import kyonggiyo.review.port.outbound.LoadReviewPort;
import kyonggiyo.review.port.outbound.SaveReviewPort;
import kyonggiyo.review.domain.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements SaveReviewPort, LoadReviewPort, DeleteReviewPort {

    private final ReviewRepository reviewRepository;

    @Override
    public Review getById(Long id) {
        return reviewRepository.getById(id);
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

}
