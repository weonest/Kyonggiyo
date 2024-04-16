package kyonggiyo.application.port.in.restaurant.dto;

import kyonggiyo.domain.restaurant.RestaurantCategory;

import java.util.List;

public record RestaurantCategoryQuery(
        List<RestaurantCategory> categories
) {
}
