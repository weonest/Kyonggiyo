package kyonggiyo.adapter.out.persistence.restaurant.review;

import kyonggiyo.application.port.out.restaurant.review.DeleteReviewPort;
import kyonggiyo.application.port.out.restaurant.review.LoadReviewPort;
import kyonggiyo.application.port.out.restaurant.review.SaveReviewPort;
import kyonggiyo.domain.restaurant.Review;
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
