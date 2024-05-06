package kyonggiyo.persistence.event.review;

import kyonggiyo.review.port.inbound.LoadReviewEventPort;
import kyonggiyo.review.port.inbound.SaveReviewEventPort;
import kyonggiyo.review.domain.event.ReviewEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewEventPersistenceAdapter implements SaveReviewEventPort, LoadReviewEventPort {

    private final ReviewEventRepository reviewEventRepository;

    @Override
    public ReviewEvent save(ReviewEvent event) {
        return reviewEventRepository.save(event);
    }

    @Override
    public ReviewEvent getById(Long id) {
        return reviewEventRepository.getById(id);
    }

    @Override
    public List<ReviewEvent> findAllFailedEvent() {
        return reviewEventRepository.findAllFailedEvent();
    }

}
