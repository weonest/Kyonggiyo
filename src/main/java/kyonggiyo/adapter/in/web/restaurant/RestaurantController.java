package kyonggiyo.adapter.in.web.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantCreateRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantResponse;
import kyonggiyo.application.port.in.restaurant.CreateRestaurantUseCase;
import kyonggiyo.application.port.in.restaurant.GetRestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final GetRestaurantUseCase getRestaurantUseCase;

    @PostMapping
    public void createRestaurant(@RequestBody RestaurantCreateRequest request) {
        createRestaurantUseCase.create(request);
    }

    @GetMapping
    public ResponseEntity<List> getRestaurantMarker() {
        List<RestaurantMarkerResponse> response = getRestaurantUseCase.getAllRestaurantsForMarker();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurant(@PathVariable Long restaurantId) {
        RestaurantResponse response = getRestaurantUseCase.getById(restaurantId);
        return ResponseEntity.ok(response);
    }

}
