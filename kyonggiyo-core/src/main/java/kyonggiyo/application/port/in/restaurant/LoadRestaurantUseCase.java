package kyonggiyo.application.port.in.restaurant;


import kyonggiyo.application.port.in.restaurant.dto.RestaurantByKeywordQuery;
import kyonggiyo.application.port.in.restaurant.dto.RestaurantCategoryQuery;
import kyonggiyo.application.port.in.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.application.port.in.restaurant.dto.RestaurantResponse;
import kyonggiyo.application.port.in.restaurant.dto.RestaurantSearchResponse;

import java.util.List;

public interface LoadRestaurantUseCase {

    RestaurantResponse getById(Long id);

    List<RestaurantMarkerResponse> getAllRestaurantsForMarker();

    List<RestaurantSearchResponse> searchByKeyword(RestaurantByKeywordQuery query);

    List<RestaurantSearchResponse> filterRestaurants(RestaurantCategoryQuery param);

}
