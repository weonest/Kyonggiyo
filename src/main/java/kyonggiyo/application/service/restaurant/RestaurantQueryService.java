package kyonggiyo.application.service.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantResponse;
import kyonggiyo.application.port.in.restaurant.GetRestaurantQuery;
import kyonggiyo.application.port.out.restaurant.GetRestaurantPort;
import kyonggiyo.domain.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantQueryService implements GetRestaurantQuery {

    private final GetRestaurantPort getRestaurantPort;

    @Override
    public RestaurantResponse getById(Long id) {
        return RestaurantResponse.from(getRestaurantPort.getById(id));
    }

    @Override
    public List<RestaurantMarkerResponse> getAllRestaurantsForMarker() {
        List<Restaurant> restaurants = getRestaurantPort.getAllRestaurants();
        return restaurants.stream()
                .map(RestaurantMarkerResponse::from).toList();
    }

}
