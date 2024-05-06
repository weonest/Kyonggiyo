package kyonggiyo.api.adapter.controller.restaurant;

import com.epages.restdocs.apispec.Schema;
import kyonggiyo.api.adapter.controller.ControllerTest;
import kyonggiyo.api.adapter.controller.review.dto.ReviewCreateRequest;
import kyonggiyo.api.adapter.controller.review.dto.ReviewUpdateRequest;
import kyonggiyo.auth.domain.vo.UserInfo;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends ControllerTest {

    @Test
    void 요청정보를_통해_리뷰를_생성한다() throws Exception{
        // given
        Long restaurantId = 1L;
        ReviewCreateRequest request = Instancio.create(ReviewCreateRequest.class);

        willDoNothing().given(createReviewUseCase).createReview(any(UserInfo.class), eq(restaurantId), eq(request.toCommand()));

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/restaurants/{restaurantId}/reviews", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN))
                .andDo(document("review-create",
                        resourceDetails().tag("리뷰").description("리뷰 생성")
                                .requestSchema(Schema.schema("ReviewCreateRequest")),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("액세스 토큰")
                        ),
                        requestFields(
                                fieldWithPath("rating").type(JsonFieldType.NUMBER).description("별점"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("imageUrls").type(JsonFieldType.ARRAY).description("이미지 주소")
                        )));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 요청정보를_통해_리뷰를_수정한다() throws Exception{
        // given
        Long restaurantId = 1L;
        Long reviewId = 1L;
        ReviewUpdateRequest request = Instancio.create(ReviewUpdateRequest.class);

        willDoNothing().given(updateReviewUseCase).updateReview(any(UserInfo.class), eq(restaurantId), eq(request.toCommand()));

        // when
        ResultActions resultActions = mockMvc.perform(
                patch("/api/v1/restaurants/{restaurantId}/reviews/{reviewId}", restaurantId, reviewId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                            .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN))
                .andDo(document("review-update",
                        resourceDetails().tag("리뷰").description("리뷰 수정")
                                .requestSchema(Schema.schema("ReviewUpdateRequest")),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("액세스 토큰")
                        ),
                        requestFields(
                                fieldWithPath("rating").type(JsonFieldType.NUMBER).description("별점"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("imageUrls").type(JsonFieldType.ARRAY).description("이미지 주소")
                        )));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 리뷰를_삭제한다() throws Exception{
        // given
        Long restaurantId = 1L;
        Long reviewId = 1L;

        willDoNothing().given(deleteReviewUseCase).deleteReview(any(UserInfo.class), eq(reviewId));

        // when
        ResultActions resultActions = mockMvc.perform(
                        delete("/api/v1/restaurants/{restaurantId}/reviews/{reviewId}", restaurantId, reviewId)
                                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN))
                .andDo(document("review-delete",
                        resourceDetails().tag("리뷰").description("리뷰 삭제"),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("액세스 토큰")
                        )));

        // then
        resultActions.andExpect(status().isOk());
    }

}
