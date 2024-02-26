package kyonggiyo.adapter.in.web.restaurant.dto;

import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.RestaurantCategory;

import java.util.List;

public record RestaurantResponse(
        Long id,
        String name,
        RestaurantCategory category,
        String contact,
        String address,
        double lat,
        double lng,
        float averageRating,
        List<ReviewResponse> reviews
) {

    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCategory(),
                restaurant.getContact(),
                restaurant.getAddress().getAddress(),
                restaurant.getAddress().getLat(),
                restaurant.getAddress().getLng(),
                restaurant.getAverageRating(),
                restaurant.getReviews().stream()
                        .map(ReviewResponse::from)
                        .toList()
        );
    }

}
