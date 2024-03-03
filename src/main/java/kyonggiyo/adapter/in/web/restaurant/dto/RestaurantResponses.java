package kyonggiyo.adapter.in.web.restaurant.dto;

import java.util.List;

public record RestaurantResponses<T>(
        List<T> data,
        int size
) {

    public static <T> RestaurantResponses<T> from(List<T> list) {
        return new RestaurantResponses<>(list, list.size());
    }

}
