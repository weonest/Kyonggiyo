package kyonggiyo.adapter.in.web.restaurant.dto.review;

import kyonggiyo.domain.image.Image;

public record ImageResponse(
        String imageUrl
) {

    public static ImageResponse from(Image image) {
        return new ImageResponse(image.getImageUrl());
    }

}
