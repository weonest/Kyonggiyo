package kyonggiyo.application.port.in.restaurant.dto;

import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.RestaurantCategory;

public record RestaurantMarkerResponse(
        Long id,
        String name,
        float averageRating,
        RestaurantCategory category,
        double lat,
        double lng,
        String reason
) {

    public static RestaurantMarkerResponse from(Restaurant restaurant) {
        return new RestaurantMarkerResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAverageRating(),
                restaurant.getCategory(),
                restaurant.getAddress().getLat(),
                restaurant.getAddress().getLng(),
                restaurant.getReason()
        );
    }

}
