package kyonggiyo.adapter.in.web.image.dto;

import kyonggiyo.domain.image.Image;

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
