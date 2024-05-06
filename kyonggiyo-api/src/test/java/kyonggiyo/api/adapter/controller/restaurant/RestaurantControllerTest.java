package kyonggiyo.api.adapter.controller.restaurant;

import com.epages.restdocs.apispec.Schema;
import kyonggiyo.api.adapter.controller.ControllerTest;
import kyonggiyo.api.adapter.controller.restaurant.dto.RestaurantByKeywordRequest;
import kyonggiyo.api.adapter.controller.restaurant.dto.RestaurantCreateRequest;
import kyonggiyo.restaurant.dto.RestaurantMarkerResponse;
import kyonggiyo.restaurant.dto.RestaurantResponse;
import kyonggiyo.restaurant.dto.RestaurantResponses;
import kyonggiyo.restaurant.dto.RestaurantCategoryQuery;
import kyonggiyo.restaurant.dto.RestaurantSearchResponse;
import kyonggiyo.restaurant.domain.vo.RestaurantCategory;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends ControllerTest {

    @Test
    void 요청정보를_통해_식당을_생성한다() throws Exception{
        // given
        RestaurantCreateRequest request = Instancio.create(RestaurantCreateRequest.class);

        willDoNothing().given(createRestaurantUseCase).createRestaurant(request.toCommand());

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
                                fieldWithPath("lng").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("reason").type(JsonFieldType.STRING).description("추천 이유")
                        )));

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 전체_식당_마커를_조회한다() throws Exception{
        // given
        List<RestaurantMarkerResponse> restaurants = Instancio.ofList(RestaurantMarkerResponse.class).create();
        RestaurantResponses<RestaurantMarkerResponse> response = RestaurantResponses.from(restaurants);

        given(loadRestaurantUseCase.getAllRestaurantsForMarker()).willReturn(restaurants);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/restaurants/markers"))
                .andDo(document("get-restaurant-marker",
                        resourceDetails().tag("식당").description("전체 식당 마커 조회")
                                .responseSchema(Schema.schema("RestaurantMarkerResponses")),
                        responseFields(
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("식별자"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("식당 이름"),
                                fieldWithPath("data[].averageRating").type(JsonFieldType.NUMBER).description("식당 평점"),
                                fieldWithPath("data[].category").type(JsonFieldType.STRING).description("식당 카테고리"),
                                fieldWithPath("data[].lat").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("data[].lng").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("data[].reason").type(JsonFieldType.STRING).description("추천 이유"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("데이터 수")
                        )));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void 검색_키워드를_통해_식당_마커를_조회한다() throws Exception{
        // given
        String keyword = "짜장면";
        List<RestaurantSearchResponse> restaurants = Instancio.ofList(RestaurantSearchResponse.class).create();
        RestaurantResponses<RestaurantSearchResponse> response = RestaurantResponses.from(restaurants);

        given(loadRestaurantUseCase.searchByKeyword(new RestaurantByKeywordRequest(keyword).toQuery())).willReturn(restaurants);

        // when
        ResultActions resultActions = mockMvc.perform(
                        get("/api/v1/restaurants/markers/search")
                                .queryParam("keyword", keyword))
                .andDo(document("get-restaurant-marker-search",
                        resourceDetails().tag("식당").description("식당 키워드 검색 (이름 or 리뷰 내용)")
                                .responseSchema(Schema.schema("RestaurantMarkerResponses")),
                        queryParameters(
                                parameterWithName("keyword").description("검색 키워드")
                        ),
                        responseFields(
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("식별자"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("식당 이름"),
                                fieldWithPath("data[].averageRating").type(JsonFieldType.NUMBER).description("식당 평점"),
                                fieldWithPath("data[].category").type(JsonFieldType.STRING).description("식당 카테고리"),
                                fieldWithPath("data[].address").type(JsonFieldType.STRING).description("추천 이유"),
                                fieldWithPath("data[].lat").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("data[].lng").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("데이터 수")
                        )));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void 카테고리_필터링을_통해_식당_마커를_조회한다() throws Exception{
        // given
        String categories = "korean,cafe";
        List<RestaurantCategory> categoryList = List.of(RestaurantCategory.KOREAN, RestaurantCategory.CAFE);
        List<RestaurantSearchResponse> restaurants = Instancio.ofList(RestaurantSearchResponse.class).create();
        RestaurantResponses<RestaurantSearchResponse> response = RestaurantResponses.from(restaurants);

        given(loadRestaurantUseCase.filterRestaurants(new RestaurantCategoryQuery(categoryList))).willReturn(restaurants);

        // when
        ResultActions resultActions = mockMvc.perform(
                        get("/api/v1/restaurants/markers/filter")
                                .queryParam("categories", categories))
                .andDo(document("get-restaurant-marker-filter",
                        resourceDetails().tag("식당").description("식당 카테고리 필터링")
                                .responseSchema(Schema.schema("RestaurantMarkerResponses")),
                        queryParameters(
                                parameterWithName("categories").description("카테고리 항목")
                        ),
                        responseFields(
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("식별자"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("식당 이름"),
                                fieldWithPath("data[].averageRating").type(JsonFieldType.NUMBER).description("식당 평점"),
                                fieldWithPath("data[].category").type(JsonFieldType.STRING).description("식당 카테고리"),
                                fieldWithPath("data[].address").type(JsonFieldType.STRING).description("추천 이유"),
                                fieldWithPath("data[].lat").type(JsonFieldType.NUMBER).description("위도"),
                                fieldWithPath("data[].lng").type(JsonFieldType.NUMBER).description("경도"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("데이터 수")
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

        given(loadRestaurantUseCase.getById(restaurantId)).willReturn(response);

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
                                fieldWithPath("reason").type(JsonFieldType.STRING).description("추천 이유"),
                                fieldWithPath("averageRating").type(JsonFieldType.NUMBER).description("식당 평점"),
                                fieldWithPath("reviews[].id").type(JsonFieldType.NUMBER).description("리뷰 식별자"),
                                fieldWithPath("reviews[].rating").type(JsonFieldType.NUMBER).description("리뷰 점수"),
                                fieldWithPath("reviews[].content").type(JsonFieldType.STRING).description("리뷰 내용"),
                                fieldWithPath("reviews[].createdAt").type(JsonFieldType.STRING).description("리뷰 생성일"),
                                fieldWithPath("reviews[].reviewer.id").type(JsonFieldType.NUMBER).description("리뷰어(유저) 식별자"),
                                fieldWithPath("reviews[].reviewer.nickname").type(JsonFieldType.STRING).description("리뷰어 닉네임"),
                                fieldWithPath("reviews[].images[].id").type(JsonFieldType.NUMBER).description("이미지 URL"),
                                fieldWithPath("reviews[].images[].imageUrl").type(JsonFieldType.STRING).description("이미지 URL"),
                                fieldWithPath("reviews[].images[].key").type(JsonFieldType.STRING).description("이미지 Key")
                        )));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

}
