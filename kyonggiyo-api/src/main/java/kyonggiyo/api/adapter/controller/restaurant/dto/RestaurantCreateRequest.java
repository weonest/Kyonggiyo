package kyonggiyo.api.adapter.controller.restaurant.dto;

import kyonggiyo.restaurant.dto.RestaurantCreateCommand;
import kyonggiyo.restaurant.domain.vo.RestaurantCategory;

public record RestaurantCreateRequest(
        String name,
        RestaurantCategory category,
        String contact,
        String address,
        double lat,
        double lng,
        String reason
){

    public RestaurantCreateCommand toCommand() {
        return new RestaurantCreateCommand(
                name,
                category,
                contact,
                address,
                lat,
                lng,
                reason
        );
    }

}
