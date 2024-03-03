package kyonggiyo.adapter.in.web.restaurant.dto.review;

public record ReviewCreateRequest(
        int rating,
        String content
) {
}
