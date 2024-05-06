package kyonggiyo.api.adapter.controller.user;

import com.epages.restdocs.apispec.Schema;
import kyonggiyo.api.adapter.controller.ControllerTest;
import kyonggiyo.api.adapter.controller.user.dto.UserCreateRequest;
import kyonggiyo.api.adapter.controller.user.dto.ValidateNicknameRequest;
import kyonggiyo.api.adapter.controller.user.dto.ValidateNicknameResponse;
import kyonggiyo.auth.domain.vo.Platform;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTest {

    @Test
    void 요청정보를_통해_유저를_생성한다() throws Exception{
        // given
        UserCreateRequest request = Instancio.create(UserCreateRequest.class);
        Platform platform = Instancio.create(Platform.class);
        URI expectUri = UriComponentsBuilder
                .fromUriString("www.example.com/oauth/authorize")
                .queryParam("client_id", "{clientId}")
                .queryParam("redirect_uri", "{redirectUri}")
                .queryParam("response_type", "code")
                .build().toUri();

        given(createUserUseCase.createUser(request.toCommand())).willReturn(platform);
        given(provideAuthCodeUrlUseCase.provideUri(platform)).willReturn(expectUri);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/users/profile")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(document("user-create",
                        resourceDetails().tag("유저").description("유저 생성")
                                .requestSchema(Schema.schema("UserCreateRequest")),
                        requestFields(
                                fieldWithPath("accountId").type(JsonFieldType.NUMBER).description("계정 식별자"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("로그인 URI")
                        )));

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(redirectedUrl(expectUri.toString()));
    }

    @Test
    void 프로필_등록시_닉네임_중복검사를_한다() throws Exception{
        // given
        ValidateNicknameRequest request = Instancio.create(ValidateNicknameRequest.class);
        ValidateNicknameResponse response = new ValidateNicknameResponse(true);

        given(validateNicknameUseCase.existByNickname(request.nickname())).willReturn(false);

        // when
        ResultActions resultActions = mockMvc.perform(
                        get("/api/v1/users/profile/nickname")
                                .param("nickname", request.nickname()))
                .andDo(document("user-validate-nickname",
                        resourceDetails().tag("유저").description("유저 닉네임 유효성 검사")
                                .requestSchema(Schema.schema("ValidateNicknameRequest"))
                                .responseSchema(Schema.schema("ValidateNicknameResponse")),
                        queryParameters(
                                parameterWithName("nickname").description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("flag").type(JsonFieldType.BOOLEAN).description("사용 가능 여부")
                        )));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

}
