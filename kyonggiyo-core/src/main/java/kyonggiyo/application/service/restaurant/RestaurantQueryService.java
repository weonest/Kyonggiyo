package kyonggiyo.application.service.restaurant;

import kyonggiyo.image.dto.ImageResponse;
import kyonggiyo.application.port.in.restaurant.LoadRestaurantUseCase;
import kyonggiyo.application.port.in.restaurant.dto.RestaurantByKeywordQuery;
import kyonggiyo.application.port.in.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.application.port.in.restaurant.dto.RestaurantResponse;
import kyonggiyo.application.port.in.restaurant.dto.RestaurantSearchResponse;
import kyonggiyo.application.port.in.review.dto.ReviewResponse;
import kyonggiyo.image.port.outbound.LoadImagePort;
import kyonggiyo.application.port.out.restaurant.LoadRestaurantPort;
import kyonggiyo.application.port.in.restaurant.dto.RestaurantCategoryQuery;
import kyonggiyo.image.domain.entity.Image;
import kyonggiyo.image.domain.vo.ImageType;
import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.review.Review;
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
    public List<RestaurantSearchResponse> searchByKeyword(RestaurantByKeywordQuery query) {
        List<Restaurant> restaurants = loadRestaurantPort.findByNameOrReviewContent(query.keyword());
        return restaurants.stream()
                .map(RestaurantSearchResponse::from).toList();
    }

    @Override
    public List<RestaurantSearchResponse> filterRestaurants(RestaurantCategoryQuery query) {
        List<Restaurant> restaurants = loadRestaurantPort.filterByCategory(query.categories());
        return restaurants.stream()
                .map(RestaurantSearchResponse::from).toList();
    }

}
