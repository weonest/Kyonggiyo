package kyonggiyo.adapter.in.web.restaurant.dto;

public record ReviewUpdateRequest (
        int rating,
        String content
) {
}
