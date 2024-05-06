package kyonggiyo.api.adapter.controller.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import kyonggiyo.restaurant.dto.RestaurantCategoryQuery;
import kyonggiyo.restaurant.domain.vo.RestaurantCategory;

import java.util.Arrays;
import java.util.List;

public record RestaurantFilterRequest(
        @NotBlank
        String categories
) {

    public RestaurantCategoryQuery toQuery() {
        List<String> categoryList = Arrays.stream(categories.split(",")).toList();
        return new RestaurantCategoryQuery(
                categoryList.stream()
                        .map(v -> RestaurantCategory.valueOf(v.toUpperCase()))
                        .toList());
    }

}
