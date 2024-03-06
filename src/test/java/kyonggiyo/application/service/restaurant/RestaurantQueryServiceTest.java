package kyonggiyo.application.service.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantByKeywordRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantResponse;
import kyonggiyo.application.port.in.restaurant.GetRestaurantUseCase;
import kyonggiyo.application.port.out.image.GetImagePort;
import kyonggiyo.application.port.out.restaurant.GetRestaurantPort;
import kyonggiyo.application.service.ServiceTest;
import kyonggiyo.application.service.restaurant.dto.RestaurantCategoryParam;
import kyonggiyo.domain.image.Image;
import kyonggiyo.domain.image.ImageType;
import kyonggiyo.domain.restaurant.Restaurant;
import kyonggiyo.domain.restaurant.RestaurantCategory;
import kyonggiyo.domain.restaurant.Review;
import kyonggiyo.fixture.RestaurantFixtures;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ContextConfiguration(classes = RestaurantQueryService.class)
class RestaurantQueryServiceTest extends ServiceTest {

    @Autowired
    private GetRestaurantUseCase getRestaurantUseCase;

    @MockBean
    private GetRestaurantPort getRestaurantPort;

    @MockBean
    private GetImagePort getImagePort;

    @Test
    void 식별자를_통해_조회한_식당_정보와_리뷰_그리고_이미지를_반환한다() {
        // given
        Restaurant restaurant = Instancio.create(Restaurant.class);
        Long id = restaurant.getId();
        Set<Review> reviews = restaurant.getReviews();
        List<Image> images = Instancio.ofList(Image.class)
                .size(3)
                .create();

        given(getRestaurantPort.getById(id)).willReturn(restaurant);
        given(getImagePort.findAllByImageTypeAndReferenceId(any(ImageType.class), any(Long.class))).willReturn(images);

        // when
        RestaurantResponse result = getRestaurantUseCase.getById(id);

        // then
        assertThat(result).isNotNull();
        assertThat(reviews).hasSameSizeAs(result.reviews());
        assertThat(result.id()).isEqualTo(id);
    }

    @Test
    void 전체_식당_정보를_가져와_마커_응답객체로_반환한다() {
        // given
        List<Restaurant> restaurants = Instancio.ofList(Restaurant.class).create();

        given(getRestaurantPort.findAll()).willReturn(restaurants);

        // when
        List<RestaurantMarkerResponse> result = getRestaurantUseCase.getAllRestaurantsForMarker();

        // then
        assertThat(result).hasSameSizeAs(restaurants);
    }

    @Test
    void 검색_키워드를_통해_조회한_식당을_마커_응답객체로_반환한다() {
        // given
        int targetSize = 4;
        String keyword = "떡볶이";
        List<Restaurant> restaurants = RestaurantFixtures.generateRestaurantEntityList(keyword, targetSize);

        given(getRestaurantPort.findByNameOrReviewContent(keyword)).willReturn(restaurants);

        // when
        List<RestaurantMarkerResponse> result = getRestaurantUseCase.searchByKeyword(new RestaurantByKeywordRequest(keyword));

        // then
        assertThat(result.size()).isEqualTo(targetSize);
    }

    @Test
    void 카테고리_필터를_통해_조회한_식당을_마커_응답객체로_반환한다() {
        // given
        int koreanSize = 5;
        int japaneseSize = 6;
        List<RestaurantCategory> categories = List.of(RestaurantCategory.KOREAN, RestaurantCategory.JAPANESE);
        List<Restaurant> korean = RestaurantFixtures.generateRestaurantEntityList(RestaurantCategory.KOREAN, koreanSize);
        List<Restaurant> japanese = RestaurantFixtures.generateRestaurantEntityList(RestaurantCategory.JAPANESE, japaneseSize);
        List<Restaurant> restaurants = Stream.of(korean, japanese)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        given(getRestaurantPort.filterByCategory(categories)).willReturn(restaurants);

        // when
        List<RestaurantMarkerResponse> result = getRestaurantUseCase.filterRestaurants(new RestaurantCategoryParam(categories));

        // then
        assertThat(result.size()).isEqualTo(koreanSize + japaneseSize);
    }

}
