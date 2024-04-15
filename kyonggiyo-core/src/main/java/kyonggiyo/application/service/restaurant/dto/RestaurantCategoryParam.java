package kyonggiyo.application.service.restaurant.dto;

import kyonggiyo.domain.restaurant.RestaurantCategory;

import java.util.Arrays;
import java.util.List;

public record RestaurantCategoryParam(
        List<RestaurantCategory> categories
) {

    public static RestaurantCategoryParam from(String categories) {
        List<String> categoryList = Arrays.stream(categories.split(",")).toList();
        return new RestaurantCategoryParam(
                categoryList.stream()
                        .map(v -> RestaurantCategory.valueOf(v.toUpperCase()))
                        .toList());
    }

}
