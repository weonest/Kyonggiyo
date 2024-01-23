package kyonggiyo.adapter.in.web.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantCreateRequest;
import kyonggiyo.application.port.in.restaurant.CreateRestaurantUseCase;
import kyonggiyo.application.port.in.restaurant.GetRestaurantQuery;
import kyonggiyo.domain.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final GetRestaurantQuery getRestaurantQuery;

    @PostMapping
    public void createRestaurant(@RequestBody RestaurantCreateRequest request) {
        createRestaurantUseCase.create(request);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = getRestaurantQuery.get(restaurantId);
        return ResponseEntity.ok(restaurant);
    }

}
