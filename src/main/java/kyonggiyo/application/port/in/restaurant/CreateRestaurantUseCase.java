package kyonggiyo.application.port.in.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantCreateRequest;

public interface CreateRestaurantUseCase {

    void create(RestaurantCreateRequest request);

}
