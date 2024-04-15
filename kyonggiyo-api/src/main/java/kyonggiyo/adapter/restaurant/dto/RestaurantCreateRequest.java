package kyonggiyo.adapter.restaurant.dto;

import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.RestaurantCategory;

public record RestaurantCreateRequest(
        String name,
        RestaurantCategory category,
        String contact,
        String address,
        double lat,
        double lng,
        String reason
){

    public Restaurant toEntity() {
        return Restaurant.builder()
                .name(name)
                .category(category)
                .contact(contact)
                .address(address)
                .lat(lat)
                .lng(lng)
                .reason(reason)
                .build();
    }

}
