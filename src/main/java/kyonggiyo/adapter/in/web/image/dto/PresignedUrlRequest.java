package kyonggiyo.adapter.in.web.image.dto;

import kyonggiyo.domain.image.ImageType;

public record PresignedUrlRequest(
        ImageType imageType,
        String filename
) {
}
