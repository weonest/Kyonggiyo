package kyonggiyo.adapter.in.web.auth;

import com.epages.restdocs.apispec.Schema;
import jakarta.servlet.http.Cookie;
import kyonggiyo.adapter.in.web.ControllerTest;
import kyonggiyo.adapter.in.web.auth.dto.LogInResponse;
import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;
import kyonggiyo.domain.auth.Platform;
import kyonggiyo.global.auth.UserInfo;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthControllerTest extends ControllerTest {

    @Test
    void 플랫폼에_따른_인가코드_요청_URL을_반환한다() throws Exception {
        // given
        Platform platform = Instancio.create(Platform.class);
        URI expectUri = UriComponentsBuilder
                .fromUriString("www.example.com/oauth/authorize")
                .queryParam("client_id", "{clientId}")
                .queryParam("redirect_uri", "{redirectUri}")
                .queryParam("response_type", "code")
                .build().toUri();

        given(provideAuthCodeUrlUseCase.provideUri(platform)).willReturn(expectUri);

        // when
        ResultActions resultActions = mockMvc.perform(
                        get("/api/v1/auth/login/{platform}", platform.name().toLowerCase()))
                .andDo(document("get-authCodeRequestUrl",
                                resourceDetails().tag("인증인가").description("플랫폼 인가코드 요청 URL 불러오기"),
                                pathParameters(
                                        parameterWithName("platform").description("소셜 로그인 플랫폼")
                                ),
                                responseHeaders(
                                        headerWithName("Location").description("인가코드 요청 URL")
                                )));

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(redirectedUrl(expectUri.toString()));

    }

    @Test
    void 콜백_호출을_통해_로그인을_진행한다() throws Exception {
        // given
        Platform platform = Instancio.create(Platform.class);
        String code = "code";
        LogInResponse logInResponse = Instancio.create(LogInResponse.class);

        given(oAuthLoginUseCase.login(platform, code)).willReturn(logInResponse);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/auth/login/{platform}/callback", platform.name().toLowerCase())
                        .queryParam("code", code))
                .andDo(document("auth-login",
                        resourceDetails().tag("인증인가").description("소셜 로그인")
                                .responseSchema(Schema.schema("LogInResponse")),
                        pathParameters(
                                parameterWithName("platform").description("소셜 로그인 플랫폼")
                        ),
                        queryParameters(
                                parameterWithName("code").description("플랫폼 인가 코드")
                        ),
                        responseFields(
                                fieldWithPath("accountId").type(JsonFieldType.NUMBER).description("계정 식별자"),
                                fieldWithPath("token.accessToken").type(JsonFieldType.STRING).description("액세스 토큰"),
                                fieldWithPath("token.accessTokenMaxAge").type(JsonFieldType.NUMBER).description("액세스 토큰 MaxAge"),
                                fieldWithPath("token.refreshToken").type(JsonFieldType.STRING).description("리프레시 토큰").optional(),
                                fieldWithPath("token.refreshTokenMaxAge").type(JsonFieldType.NUMBER).description("리프레시 토큰 MaxAge")
                        )));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(logInResponse)))
                .andExpect(cookie().exists("Refresh-Token"));
    }

    @Test
    void 유저정보를_통해_로그아웃을_진행한다() throws Exception {
        // given
        Cookie cookie = new Cookie("Refresh-Token", REFRESH_TOKEN);
        cookie.setMaxAge(100);

        willDoNothing().given(oAuthLogoutUseCase).logout(any(UserInfo.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                        .cookie(cookie))
                .andDo(document("auth-logout",
                        resourceDetails().tag("인증인가").description("소셜 로그아웃"),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("액세스 토큰")
                        ),
                        requestCookies(
                                cookieWithName("Refresh-Token").description("리프레시 토큰 쿠키")
                        )));

        // then
        resultActions.andExpect(status().isNoContent())
                .andExpect(cookie().maxAge("Refresh-Token", 0));
    }

}
