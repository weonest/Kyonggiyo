package kyonggiyo.adapter.candidate.dto;

import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.restaurant.RestaurantCategory;

public record CandidateCreateRequest(
        String name,
        RestaurantCategory category,
        String contact,
        String address,
        double lat,
        double lng,
        String reason
) {

    public Candidate toEntity(Long requestId) {
        return Candidate.builder()
                .name(name)
                .category(category)
                .contact(contact)
                .address(address)
                .lat(lat)
                .lng(lng)
                .reason(reason)
                .requesterId(requestId)
                .build();
    }

}
