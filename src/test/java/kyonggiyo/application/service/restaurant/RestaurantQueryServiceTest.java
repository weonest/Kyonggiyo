package kyonggiyo.application.service.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantResponse;
import kyonggiyo.application.port.in.restaurant.GetRestaurantUseCase;
import kyonggiyo.application.port.out.restaurant.GetRestaurantPort;
import kyonggiyo.application.service.ServiceTest;
import kyonggiyo.domain.restaurant.Restaurant;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@ContextConfiguration(classes = RestaurantQueryService.class)
class RestaurantQueryServiceTest extends ServiceTest {

    @Autowired
    private GetRestaurantUseCase getRestaurantUseCase;

    @MockBean
    private GetRestaurantPort getRestaurantPort;

    @Test
    void 식별자를_통해_조회한_식당_정보를_응답객체로_반환할_수_있다() {
        // given
        Restaurant restaurant = Instancio.create(Restaurant.class);
        Long id = restaurant.getId();

        given(getRestaurantPort.getById(id)).willReturn(restaurant);

        // when
        RestaurantResponse result = getRestaurantUseCase.getById(id);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(id);
    }

    @Test
    void 마커_생성을_위한_전체_식당_정보를_가져올_수_있다() {
        // given
        List<Restaurant> restaurants = Instancio.ofList(Restaurant.class).create();

        given(getRestaurantPort.getAllRestaurants()).willReturn(restaurants);

        // when
        List<RestaurantMarkerResponse> result = getRestaurantUseCase.getAllRestaurantsForMarker();

        // then
        assertThat(result).hasSameSizeAs(restaurants);
    }

}
