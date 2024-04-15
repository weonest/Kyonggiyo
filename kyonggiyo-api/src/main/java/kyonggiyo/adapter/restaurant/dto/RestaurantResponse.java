package kyonggiyo.adapter.restaurant.dto;

import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewResponse;
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
        String reason,
        float averageRating,
        List<ReviewResponse> reviews
) {

    public static RestaurantResponse of(Restaurant restaurant, List<ReviewResponse> reviews) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCategory(),
                restaurant.getContact(),
                restaurant.getAddress().getAddress(),
                restaurant.getAddress().getLat(),
                restaurant.getAddress().getLng(),
                restaurant.getReason(),
                restaurant.getAverageRating(),
                reviews
        );
    }

}
