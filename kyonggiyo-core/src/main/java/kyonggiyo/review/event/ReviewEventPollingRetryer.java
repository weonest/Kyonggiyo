package kyonggiyo.review.event;

import kyonggiyo.review.port.inbound.LoadReviewEventPort;
import kyonggiyo.review.domain.event.ReviewEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewEventPollingRetryer {

    private final LoadReviewEventPort loadReviewEventPort;
    private final ReviewEventRetryHandler reviewEventRetryHandler;

    @Scheduled(cron = "0 0/1 * * * *")
    public void retryFailedReviewEvent() {
        List<ReviewEvent> events = loadReviewEventPort.findAllFailedEvent();
        for (ReviewEvent event : events) {
            try {
                reviewEventRetryHandler.handle(event);
            } catch (Exception e) {
                log.error("재시도 실패 리뷰 이벤트 발생 : id = " + event.getId(), e);
            }
        }
    }

}
