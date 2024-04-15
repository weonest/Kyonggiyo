package kyonggiyo.application.service.event.image;

import kyonggiyo.application.port.out.event.image.LoadImageEventPort;
import kyonggiyo.domain.event.ImageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImagePollingRetryer {

    private final LoadImageEventPort loadImageEventPort;
    private final ImageEventRetryHandler imageEventRetryHandler;

    @Scheduled(cron = "0 0/1 * * * *")
    public void publishImage() {
        List<ImageEvent> events = loadImageEventPort.findAllFailedEvent();
        for (ImageEvent event : events) {
            try {
                imageEventRetryHandler.handle(event);
            } catch (Exception e) {
                log.error("재시도 실패 이미지 발생 : id = " + event.getId(), e);
            }
        }
    }

}
