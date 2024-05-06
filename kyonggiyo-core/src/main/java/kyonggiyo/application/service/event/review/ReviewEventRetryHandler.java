package kyonggiyo.application.service.event.review;

import kyonggiyo.image.service.ImageService;
import kyonggiyo.domain.event.EventCommand;
import kyonggiyo.domain.event.ReviewEvent;
import kyonggiyo.image.domain.vo.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewEventRetryHandler {

    private final ImageService imageService;
    private final ReviewEventService reviewEventService;

    @Transactional
    public void handle(ReviewEvent event) {
        EventCommand command = event.getPayload().getCommand();

        switch (command) {
            case REVIEW_IMAGE_CREATE -> {
                List<String> imageUrls = reviewEventService.convertImageUrlsToList(event.getPayload().getAttribute());
                imageService.createImages(imageUrls, ImageType.REVIEW, event.getPayload().getEntityId());
                reviewEventService.successEvent(event.getId());
            }
        }
    }

}
