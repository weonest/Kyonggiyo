package kyonggiyo.candidate.dto;

import kyonggiyo.restaurant.domain.vo.RestaurantCategory;

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

