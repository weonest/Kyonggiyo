package kyonggiyo.adapter.in.web.restaurant.dto;

import kyonggiyo.domain.restaurant.Restaurant;

public record RestaurantCreateRequest(
        String name,
        String address,
        double x,
        double y
){

    public Restaurant toEntity() {
        return Restaurant.builder().build();
    }

}
