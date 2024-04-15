package kyonggiyo.adapter.candidate.dto;

import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.restaurant.RestaurantCategory;

import java.time.LocalDateTime;

public record CandidateResponse(
        Long id,
        String name,
        RestaurantCategory category,
        String contact,
        String address,
        double lat,
        double lng,
        String reason,
        LocalDateTime createdAt,
        Long requesterId
) {

    public static CandidateResponse from(Candidate candidate) {
        return new CandidateResponse(
                candidate.getId(),
                candidate.getName(),
                candidate.getCategory(),
                candidate.getContact(),
                candidate.getAddress().getAddress(),
                candidate.getAddress().getLat(),
                candidate.getAddress().getLng(),
                candidate.getReason(),
                candidate.getCreatedAt(),
                candidate.getRequesterId()
        );
    }

}
