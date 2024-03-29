package kyonggiyo.application.service.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantByKeywordRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantResponse;
import kyonggiyo.application.port.in.restaurant.LoadRestaurantUseCase;
import kyonggiyo.application.port.out.image.LoadImagePort;
import kyonggiyo.application.port.out.restaurant.LoadRestaurantPort;
import kyonggiyo.application.service.restaurant.dto.RestaurantCategoryParam;
import kyonggiyo.domain.image.Image;
import kyonggiyo.domain.image.ImageType;
import kyonggiyo.domain.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantQueryService implements LoadRestaurantUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final LoadImagePort loadImagePort;

    @Override
    public RestaurantResponse getById(Long id) {
        Restaurant restaurant = loadRestaurantPort.getById(id);

        Queue<List<Image>> imagesList = restaurant.getReviews().stream()
                .map(v -> loadImagePort.findAllByImageTypeAndReferenceId(ImageType.REVIEW, v.getId()))
                .collect(Collectors.toCollection(LinkedList::new));

        return RestaurantResponse.of(restaurant, imagesList);
    }

    @Override
    public List<RestaurantMarkerResponse> getAllRestaurantsForMarker() {
        List<Restaurant> restaurants = loadRestaurantPort.findAll();
        return restaurants.stream()
                .map(RestaurantMarkerResponse::from).toList();
    }

    @Override
    public List<RestaurantMarkerResponse> searchByKeyword(RestaurantByKeywordRequest request) {
        List<Restaurant> restaurants = loadRestaurantPort.findByNameOrReviewContent(request.keyword());
        return restaurants.stream()
                .map(RestaurantMarkerResponse::from).toList();
    }

    @Override
    public List<RestaurantMarkerResponse> filterRestaurants(RestaurantCategoryParam param) {
        List<Restaurant> restaurants = loadRestaurantPort.filterByCategory(param.categories());
        return restaurants.stream()
                .map(RestaurantMarkerResponse::from).toList();
    }

}
