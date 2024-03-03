package kyonggiyo.adapter.in.web.restaurant.dto.review;

public record ReviewUpdateRequest (
        int rating,
        String content
) {
}
