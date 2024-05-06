package kyonggiyo.restaurant.dto;

import kyonggiyo.restaurant.domain.entity.Restaurant;
import kyonggiyo.restaurant.domain.vo.RestaurantCategory;

public record RestaurantSearchResponse(
        Long id,
        String name,
        float averageRating,
        RestaurantCategory category,
        String address,
        double lat,
        double lng
) {

    public static RestaurantSearchResponse from(Restaurant restaurant) {
        return new RestaurantSearchResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAverageRating(),
                restaurant.getCategory(),
                restaurant.getAddress().getAddress(),
                restaurant.getAddress().getLat(),
                restaurant.getAddress().getLng()
        );
    }

}
