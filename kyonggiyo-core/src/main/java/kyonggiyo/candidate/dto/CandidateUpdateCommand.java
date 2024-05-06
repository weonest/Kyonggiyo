package kyonggiyo.candidate.dto;

import kyonggiyo.domain.restaurant.RestaurantCategory;

public record CandidateUpdateCommand(
        String name,
        RestaurantCategory category,
        String contact,
        String address,
        double lat,
        double lng,
        String reason
) {
}

