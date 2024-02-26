package kyonggiyo.adapter.in.web.restaurant.dto;

import kyonggiyo.domain.restaurant.Restaurant;

public record RestaurantMarkerResponse(
        Long id,
        String name,
        float averageRating,
        double lat,
        double lng
) {

    public static RestaurantMarkerResponse from(Restaurant restaurant) {
        return new RestaurantMarkerResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAverageRating(),
                restaurant.getAddress().getLat(),
                restaurant.getAddress().getLng()
        );
    }

}
