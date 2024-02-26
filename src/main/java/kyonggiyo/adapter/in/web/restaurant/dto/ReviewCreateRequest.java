package kyonggiyo.adapter.in.web.restaurant.dto;

public record ReviewCreateRequest(
        int rating,
        String content
) {
}
