package kyonggiyo.application.service.event;

import kyonggiyo.domain.image.ImageType;

import java.util.List;

public record ImageCreateEvent (
        List<String> imageUrls,
        ImageType imageType,
        Long referenceId
) {

    public static ImageCreateEvent of(List<String> imageUrls, ImageType imageType, Long referenceId) {
        return new ImageCreateEvent(imageUrls,
                imageType,
                referenceId);
    }

}
