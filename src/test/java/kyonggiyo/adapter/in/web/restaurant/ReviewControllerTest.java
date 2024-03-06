package kyonggiyo.adapter.in.web.restaurant;

import com.epages.restdocs.apispec.Schema;
import kyonggiyo.adapter.in.web.ControllerTest;
import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewCreateRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewUpdateRequest;
import kyonggiyo.global.auth.UserInfo;
import org.apache.http.entity.ContentType;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.generate.RestDocumentationGenerator;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends ControllerTest {

    @Test
    void 요청정보를_통해_리뷰를_생성한다() throws Exception{
        // given
        Long restaurantId = 1L;
        MockMultipartFile image = new MockMultipartFile("image", "image.png", ContentType.IMAGE_PNG.toString(), "<<data>>".getBytes());
        ReviewCreateRequest request = Instancio.create(ReviewCreateRequest.class);

        willDoNothing().given(createReviewUseCase).createReview(any(UserInfo.class), eq(restaurantId), eq(request), any(List.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.multipart("/api/v1/restaurants/{restaurantId}/reviews", restaurantId)
                        .file(image)
                        .file(new MockMultipartFile("request", "", "application/json", objectMapper.writeValueAsString(request).getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN))
                .andDo(document("review-create",
                resourceDetails().tag("리뷰").description("리뷰 생성")
                        .requestSchema(Schema.schema("ReviewCreateRequest")),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("액세스 토큰")
                ),
                requestParts(
                        partWithName("image").description("이미지 파일"),
                        partWithName("request").description("리뷰 생성 요청 JSON")
                )));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 요청정보를_통해_리뷰를_수정한다() throws Exception{
        // given
        Long restaurantId = 1L;
        Long reviewId = 1L;
        MockMultipartFile image = new MockMultipartFile("image", "image.png", ContentType.IMAGE_PNG.toString(), "<<data>>".getBytes());
        ReviewUpdateRequest request = Instancio.create(ReviewUpdateRequest.class);

        willDoNothing().given(updateReviewUseCase).updateReview(any(UserInfo.class), eq(restaurantId), eq(request), any(List.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                multipart(HttpMethod.PATCH, "/api/v1/restaurants/{restaurantId}/reviews/{reviewId}", restaurantId, reviewId)
                        .file(image)
                        .file(new MockMultipartFile("request", "", "application/json", objectMapper.writeValueAsString(request).getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                        .requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, "/api/v1/restaurants/{restaurantId}/reviews/{reviewId}"))
                .andDo(document("review-update",
                        resourceDetails().tag("리뷰").description("리뷰 수정")
                                .requestSchema(Schema.schema("ReviewUpdateRequest")),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("액세스 토큰")
                        ),
                        requestParts(
                                partWithName("image").description("이미지 파일"),
                                partWithName("request").description("리뷰 생성 요청 JSON")
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
