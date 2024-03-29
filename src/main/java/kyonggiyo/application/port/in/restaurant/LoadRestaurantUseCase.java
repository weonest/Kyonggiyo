package kyonggiyo.application.port.in.restaurant;


import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantByKeywordRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantResponse;
import kyonggiyo.application.service.restaurant.dto.RestaurantCategoryParam;

import java.util.List;

public interface LoadRestaurantUseCase {

    RestaurantResponse getById(Long id);

    List<RestaurantMarkerResponse> getAllRestaurantsForMarker();

    List<RestaurantMarkerResponse> searchByKeyword(RestaurantByKeywordRequest request);

    List<RestaurantMarkerResponse> filterRestaurants(RestaurantCategoryParam param);

}
