package kyonggiyo.adapter.in.web.auth;

import jakarta.servlet.http.Cookie;
import kyonggiyo.adapter.in.web.ControllerTest;
import kyonggiyo.adapter.in.web.auth.dto.LogInResponse;
import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;
import kyonggiyo.domain.auth.Platform;
import kyonggiyo.global.auth.UserInfo;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                get("/api/v1/auth/login/{platform}", platform.name().toLowerCase()));

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
                        .queryParam("code", code));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(logInResponse)))
                .andExpect(cookie().exists("Refresh-Token"));
    }

    @Test
    void 유저정보를_통해_로그아웃을_진행한다() throws Exception {
        // given
        UserInfo userInfo = Instancio.create(UserInfo.class);
        Cookie cookie = new Cookie("Refresh-Token", "token");
        cookie.setMaxAge(100);

        willDoNothing().given(oAuthLogoutUseCase).logout(userInfo);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/auth/logout")
                        .cookie(cookie));

        // then
        resultActions.andExpect(status().isNoContent())
                .andExpect(cookie().maxAge("Refresh-Token", 0));
    }

    @Test
    void 쿠키에_담긴_리프레시_토큰을_통해_토큰_재발급을_진행한다() throws Exception {
        // given
        Cookie cookie = new Cookie("Refresh-Token", "token");
        cookie.setMaxAge(100);
        TokenResponse tokenResponse = Instancio.create(TokenResponse.class);
        int tokenMaxAge = (int) tokenResponse.refreshTokenMaxAge();

        given(tokenService.reissueToken(cookie.getValue())).willReturn(tokenResponse);

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/auth/reissue")
                        .cookie(cookie));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(cookie().maxAge("Refresh-Token", tokenMaxAge));
    }

}
