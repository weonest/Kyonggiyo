package kyonggiyo.image.dto;

import kyonggiyo.image.domain.entity.Image;

public record ImageResponse(
        Long id,
        String imageUrl,
        String key
) {

    public static ImageResponse from(Image image) {
        return new ImageResponse(image.getId(),
                image.getImageUrl(),
                image.getKey());
    }

}
