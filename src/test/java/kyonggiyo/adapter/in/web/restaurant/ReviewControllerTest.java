package kyonggiyo.adapter.in.web.restaurant;

import kyonggiyo.adapter.in.web.ControllerTest;
import kyonggiyo.adapter.in.web.restaurant.dto.ReviewCreateRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.ReviewUpdateRequest;
import kyonggiyo.global.auth.UserInfo;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends ControllerTest {

    @Test
    void 요청정보를_통해_리뷰를_생성한다() throws Exception{
        // given
        Long restaurantId = 1L;
        ReviewCreateRequest request = Instancio.create(ReviewCreateRequest.class);

        willDoNothing().given(createReviewUseCase).create(any(UserInfo.class), eq(restaurantId), eq(request));

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/restaurants/{restaurantId}/reviews", restaurantId)
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 요청정보를_통해_리뷰를_수정한다() throws Exception{
        // given
        Long restaurantId = 1L;
        Long reviewId = 1L;
        ReviewUpdateRequest request = Instancio.create(ReviewUpdateRequest.class);

        willDoNothing().given(updateReviewUseCase).update(restaurantId, request);

        // when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/restaurants/{restaurantId}/reviews/{reviewId}", restaurantId, reviewId)
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
    }

}
