package kyonggiyo.review.dto;

import java.util.List;

public record ReviewCreateCommand(
        int rating,
        String content,
        List<String> imageUrls
) {
}