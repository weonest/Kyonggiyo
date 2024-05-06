package kyonggiyo.restaurant.dto;

import kyonggiyo.restaurant.domain.vo.RestaurantCategory;

import java.util.List;

public record RestaurantCategoryQuery(
        List<RestaurantCategory> categories
) {
}
