package kyonggiyo.adapter.restaurant.dto.review;

import java.util.List;

public record ReviewUpdateRequest (
        int rating,
        String content,
        List<String> imageUrls
) {
}
