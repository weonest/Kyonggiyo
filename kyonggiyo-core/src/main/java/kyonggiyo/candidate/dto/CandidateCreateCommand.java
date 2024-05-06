package kyonggiyo.candidate.dto;

import kyonggiyo.candidate.domain.entity.Candidate;
import kyonggiyo.domain.restaurant.RestaurantCategory;

public record CandidateCreateCommand (
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
