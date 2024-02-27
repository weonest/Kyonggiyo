package kyonggiyo.adapter.in.web.restaurant;

import kyonggiyo.adapter.in.web.ControllerTest;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantCreateRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends ControllerTest {

    @Test
    void 요청정보를_통해_식당을_생성한다() throws Exception{
        // given
        RestaurantCreateRequest request = Instancio.create(RestaurantCreateRequest.class);

        willDoNothing().given(createRestaurantUseCase).create(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/restaurants")
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 전체_식당_마커를_조회한다() throws Exception{
        // given
        List<RestaurantMarkerResponse> response = Instancio.ofList(RestaurantMarkerResponse.class).create();

        given(getRestaurantUseCase.getAllRestaurantsForMarker()).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/restaurants")
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void 식당_상세정보를_조회한다() throws Exception{
        // given
        RestaurantResponse response = Instancio.create(RestaurantResponse.class);
        Long restaurantId = response.id();

        given(getRestaurantUseCase.getById(restaurantId)).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/restaurants/{restaurantId}", restaurantId)
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

}
