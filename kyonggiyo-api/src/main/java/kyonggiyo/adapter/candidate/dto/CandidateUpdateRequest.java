package kyonggiyo.adapter.candidate.dto;

import kyonggiyo.domain.restaurant.RestaurantCategory;

public record CandidateUpdateRequest(
        String name,
        RestaurantCategory category,
        String contact,
        String address,
        double lat,
        double lng,
        String reason
) {
}
