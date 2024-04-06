package kyonggiyo.application.service.restaurant;

import kyonggiyo.adapter.in.web.image.dto.ImageResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantByKeywordRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantSearchResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewResponse;
import kyonggiyo.application.port.in.restaurant.LoadRestaurantUseCase;
import kyonggiyo.application.port.out.image.LoadImagePort;
import kyonggiyo.application.port.out.restaurant.LoadRestaurantPort;
import kyonggiyo.application.service.restaurant.dto.RestaurantCategoryParam;
import kyonggiyo.domain.image.Image;
import kyonggiyo.domain.image.ImageType;
import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
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

        Set<Review> reviews = restaurant.getReviews();

        List<Long> reviewIds = reviews.stream()
                .map(v -> v.getId())
                .toList();

        Map<Long, List<Image>> images = loadImagePort.findAllByImageTypeAndReferenceIdIn(ImageType.REVIEW, reviewIds)
                .stream()
                .collect(Collectors.groupingBy(Image::getReferenceId));

        List<ReviewResponse> reviewResponses = reviews.stream()
                .map(v -> {
                    List<ImageResponse> imageResponses = Optional.ofNullable(images.get(v.getId()))
                            .filter(Predicate.not(List::isEmpty))
                            .map(list -> list.stream().map(ImageResponse::from).toList())
                            .orElse(null);
                    return ReviewResponse.of(v, imageResponses);
                }).toList();

        return RestaurantResponse.of(restaurant, reviewResponses);
    }

    @Override
    public List<RestaurantMarkerResponse> getAllRestaurantsForMarker() {
        List<Restaurant> restaurants = loadRestaurantPort.findAll();
        return restaurants.stream()
                .map(RestaurantMarkerResponse::from).toList();
    }

    @Override
    public List<RestaurantSearchResponse> searchByKeyword(RestaurantByKeywordRequest request) {
        List<Restaurant> restaurants = loadRestaurantPort.findByNameOrReviewContent(request.keyword());
        return restaurants.stream()
                .map(RestaurantSearchResponse::from).toList();
    }

    @Override
    public List<RestaurantSearchResponse> filterRestaurants(RestaurantCategoryParam param) {
        List<Restaurant> restaurants = loadRestaurantPort.filterByCategory(param.categories());
        return restaurants.stream()
                .map(RestaurantSearchResponse::from).toList();
    }

}
