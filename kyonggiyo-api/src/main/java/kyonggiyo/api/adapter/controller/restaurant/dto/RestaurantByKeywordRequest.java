package kyonggiyo.api.adapter.controller.restaurant.dto;

import kyonggiyo.restaurant.dto.RestaurantByKeywordQuery;

public record RestaurantByKeywordRequest (
        String keyword
) {

    public RestaurantByKeywordQuery toQuery() {
        return new RestaurantByKeywordQuery(
                keyword
        );
    }

}
