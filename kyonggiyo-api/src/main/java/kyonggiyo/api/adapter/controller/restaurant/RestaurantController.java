package kyonggiyo.api.adapter.controller.restaurant;

import jakarta.validation.Valid;
import kyonggiyo.api.adapter.controller.restaurant.dto.RestaurantByKeywordRequest;
import kyonggiyo.api.adapter.controller.restaurant.dto.RestaurantCreateRequest;
import kyonggiyo.api.adapter.controller.restaurant.dto.RestaurantFilterRequest;
import kyonggiyo.restaurant.port.inbound.CreateRestaurantUseCase;
import kyonggiyo.restaurant.port.inbound.LoadRestaurantUseCase;
import kyonggiyo.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.restaurant.dto.RestaurantResponse;
import kyonggiyo.restaurant.dto.RestaurantResponses;
import kyonggiyo.restaurant.dto.RestaurantSearchResponse;
import kyonggiyo.auth.Admin;
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
    private final LoadRestaurantUseCase loadRestaurantUseCase;

    @Admin
    @PostMapping
    public ResponseEntity<Void> restaurantCreate(@RequestBody RestaurantCreateRequest request) {
        createRestaurantUseCase.createRestaurant(request.toCommand());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/markers")
    public ResponseEntity<RestaurantResponses<RestaurantMarkerResponse>> restaurantMarkers() {
        List<RestaurantMarkerResponse> response = loadRestaurantUseCase.getAllRestaurantsForMarker();
        return ResponseEntity.ok(RestaurantResponses.from(response));
    }

    @GetMapping("/markers/search")
    public ResponseEntity<RestaurantResponses<RestaurantSearchResponse>> restaurantSearch(RestaurantByKeywordRequest request) {
        List<RestaurantSearchResponse> response = loadRestaurantUseCase.searchByKeyword(request.toQuery());
        return ResponseEntity.ok(RestaurantResponses.from(response));
    }

    @GetMapping("/markers/filter")
    public ResponseEntity<RestaurantResponses<RestaurantSearchResponse>> restaurantSearch(@Valid RestaurantFilterRequest request) {
        List<RestaurantSearchResponse> response = loadRestaurantUseCase.filterRestaurants(request.toQuery());
        return ResponseEntity.ok(RestaurantResponses.from(response));
    }

    @GetMapping("/markers/{restaurantId}")
    public ResponseEntity<RestaurantResponse> restaurantDetail(@PathVariable Long restaurantId) {
        RestaurantResponse response = loadRestaurantUseCase.getById(restaurantId);
        return ResponseEntity.ok(response);
    }

}
