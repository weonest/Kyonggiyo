package kyonggiyo.adapter.in.web.restaurant;

import com.epages.restdocs.apispec.Schema;
import kyonggiyo.adapter.in.web.ControllerTest;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantCreateRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends ControllerTest {

    @Test
    void 요청정보를_통해_식당을_생성한다() throws Exception{
        // given
        RestaurantCreateRequest request = Instancio.create(RestaurantCreateRequest.class);

        willDoNothing().given(createRestaurantUseCase).createRestaurant(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/restaurants")
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(document("restaurant-create",
                        resourceDetails().tag("식당").description("식당 생성")
                                .requestSchema(Schema.schema("RestaurantCreateRequest")),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("액세스 토큰")
                        ),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("식당 이름"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리"),
                                fieldWithPath("contact").type(JsonFieldType.STRING).description("연락처").optional(),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("lat").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("lng").type(JsonFieldType.NUMBER).description("경도")
                        )));

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
                get("/api/v1/restaurants/markers"))
                .andDo(document("get-restaurant-marker",
                        resourceDetails().tag("식당").description("전체 식당 마커 조회")
                                .responseSchema(Schema.schema("RestaurantMarkerResponse")),
                        responseFields(
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("식별자"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("식당 이름"),
                                fieldWithPath("[].averageRating").type(JsonFieldType.NUMBER).description("식당 평점"),
                                fieldWithPath("[].category").type(JsonFieldType.STRING).description("식당 카테고리"),
                                fieldWithPath("[].lat").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("[].lng").type(JsonFieldType.NUMBER).description("경도")
                        )));

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
                get("/api/v1/restaurants/markers/{restaurantId}", restaurantId))
                .andDo(document("get-restaurant-detail",
                        resourceDetails().tag("식당").description("식당 상세 조회")
                                .responseSchema(Schema.schema("RestaurantResponse")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("식당 식별자"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("식당 이름"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리"),
                                fieldWithPath("contact").type(JsonFieldType.STRING).description("연락처").optional(),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("lat").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("lng").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("averageRating").type(JsonFieldType.NUMBER).description("식당 평점"),
                                fieldWithPath("reviews[].id").type(JsonFieldType.NUMBER).description("리뷰 식별자"),
                                fieldWithPath("reviews[].rating").type(JsonFieldType.NUMBER).description("리뷰 점수"),
                                fieldWithPath("reviews[].content").type(JsonFieldType.STRING).description("리뷰 내용"),
                                fieldWithPath("reviews[].createdAt").type(JsonFieldType.STRING).description("리뷰 생성일"),
                                fieldWithPath("reviews[].reviewer.id").type(JsonFieldType.NUMBER).description("리뷰어(유저) 식별자"),
                                fieldWithPath("reviews[].reviewer.nickname").type(JsonFieldType.STRING).description("리뷰어 닉네임")
                        )));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

}
