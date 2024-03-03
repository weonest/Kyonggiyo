package kyonggiyo.application.service.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantByKeywordRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantFilterRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantResponse;
import kyonggiyo.application.port.in.restaurant.GetRestaurantUseCase;
import kyonggiyo.application.port.out.restaurant.GetRestaurantPort;
import kyonggiyo.application.service.restaurant.dto.RestaurantCategoryParam;
import kyonggiyo.domain.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantQueryService implements GetRestaurantUseCase {

    private final GetRestaurantPort getRestaurantPort;

    @Override
    public RestaurantResponse getById(Long id) {
        return RestaurantResponse.from(getRestaurantPort.getById(id));
    }

    @Override
    public List<RestaurantMarkerResponse> getAllRestaurantsForMarker() {
        List<Restaurant> restaurants = getRestaurantPort.findAll();
        return restaurants.stream()
                .map(RestaurantMarkerResponse::from).toList();
    }

    @Override
    public List<RestaurantMarkerResponse> searchByKeyword(RestaurantByKeywordRequest request) {
        List<Restaurant> restaurants = getRestaurantPort.findByNameOrReviewContent(request.keyword());
        return restaurants.stream()
                .map(RestaurantMarkerResponse::from).toList();
    }

    @Override
    public List<RestaurantMarkerResponse> filterRestaurants(RestaurantCategoryParam param) {
        List<Restaurant> restaurants = getRestaurantPort.filterByCategory(param.categories());
        return restaurants.stream()
                .map(RestaurantMarkerResponse::from).toList();
    }

}
