package kyonggiyo.adapter.in.web.restaurant.dto;

import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewResponse;
import kyonggiyo.domain.image.Image;
import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.RestaurantCategory;

import java.util.List;
import java.util.Queue;

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

    public static RestaurantResponse of(Restaurant restaurant, Queue<List<Image>> imagesList) {
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
                restaurant.getReviews().stream()
                        .map(v -> ReviewResponse.of(v, imagesList.poll()))
                        .toList()
        );
    }

}
