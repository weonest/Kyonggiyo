package kyonggiyo.adapter.in.web.candidate.dto;

import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.restaurant.RestaurantCategory;

public record CandidateCreateRequest(
        String name,
        RestaurantCategory category,
        String contact,
        String address,
        double lat,
        double lng
) {

    public Candidate toEntity(Long requestId) {
        return Candidate.builder()
                .name(name)
                .category(category)
                .contact(contact)
                .address(address)
                .lat(lat)
                .lng(lng)
                .requesterId(requestId)
                .build();
    }

}
