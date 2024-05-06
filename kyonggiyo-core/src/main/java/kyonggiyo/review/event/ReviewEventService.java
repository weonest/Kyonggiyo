package kyonggiyo.review.event;

import kyonggiyo.review.port.inbound.LoadReviewEventPort;
import kyonggiyo.review.port.inbound.SaveReviewEventPort;
import kyonggiyo.review.domain.event.ReviewEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewEventService {

    private final SaveReviewEventPort saveReviewEventPort;
    private final LoadReviewEventPort loadReviewEventPort;

    @Transactional
    public void createEvent(ReviewEvent event) {
        saveReviewEventPort.save(event);
    }

    @Transactional
    public void successEvent(Long id) {
        ReviewEvent event = loadReviewEventPort.getById(id);
        event.successEvent();
    }

    public String convertImageUrlsToString(List<String> imageUrls) {
        return String.join(",", imageUrls);
    }

    public List<String> convertImageUrlsToList(String imageUrls) {
        return Arrays.stream(imageUrls.split(",")).toList();
    }

}
