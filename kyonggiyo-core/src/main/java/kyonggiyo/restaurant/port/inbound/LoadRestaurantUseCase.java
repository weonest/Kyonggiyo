package kyonggiyo.restaurant.port.inbound;


import kyonggiyo.restaurant.dto.RestaurantByKeywordQuery;
import kyonggiyo.restaurant.dto.RestaurantCategoryQuery;
import kyonggiyo.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.restaurant.dto.RestaurantResponse;
import kyonggiyo.restaurant.dto.RestaurantSearchResponse;

import java.util.List;

public interface LoadRestaurantUseCase {

    RestaurantResponse getById(Long id);

    List<RestaurantMarkerResponse> getAllRestaurantsForMarker();

    List<RestaurantSearchResponse> searchByKeyword(RestaurantByKeywordQuery query);

    List<RestaurantSearchResponse> filterRestaurants(RestaurantCategoryQuery param);

}
