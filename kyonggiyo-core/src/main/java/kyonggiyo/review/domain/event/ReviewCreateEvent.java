package kyonggiyo.review.domain.event;

import kyonggiyo.common.entity.event.EventCommand;
import kyonggiyo.image.domain.vo.ImageType;

import java.util.List;

public record ReviewCreateEvent(
        Long id,
        List<String> imageUrls,
        ImageType imageType,
        Long referenceId
) {

    public static ReviewCreateEvent of(Long id,
                                       List<String> imageUrls,
                                       ImageType imageType,
                                       Long referenceId) {
        return new ReviewCreateEvent(id,
                imageUrls,
                imageType,
                referenceId);
    }

    public ReviewEvent toEvent(String imageUrls) {
        return ReviewEvent.builder()
                .id(id)
                .entityId(referenceId)
                .command(EventCommand.REVIEW_IMAGE_CREATE)
                .attribute(imageUrls)
                .build();
    }

}
