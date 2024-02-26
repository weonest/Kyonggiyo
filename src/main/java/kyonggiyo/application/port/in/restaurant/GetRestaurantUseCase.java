package kyonggiyo.application.port.in.restaurant;


import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantResponse;

import java.util.List;

public interface GetRestaurantUseCase {

    RestaurantResponse getById(Long id);

    List<RestaurantMarkerResponse> getAllRestaurantsForMarker();

}
