package kyonggiyo.adapter.in.web.restaurant.dto;

import jakarta.validation.constraints.NotBlank;

public record RestaurantByKeywordRequest (
        @NotBlank
        String keyword
) {
}
