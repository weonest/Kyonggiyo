package kyonggiyo.adapter.restaurant.dto;

import jakarta.validation.constraints.NotBlank;

public record RestaurantFilterRequest (
        @NotBlank
        String categories
) {
}
