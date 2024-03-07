package kyonggiyo.global.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;
import kyonggiyo.application.service.auth.TokenService;
import kyonggiyo.domain.auth.exception.ExpiredTokenException;
import kyonggiyo.domain.auth.exception.TokenErrorCode;
import kyonggiyo.domain.user.Role;
import kyonggiyo.global.exception.AuthenticationException;
import org.apache.http.HttpHeaders;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter authenticationFilter;

    @Spy
    private AuthContext authContext;

    @Mock
    private TokenService tokenService;

    @Test
    void 화이트리스트_요청에_해당하면_필터는_동작하지_않는다() {
        // given
        String[] whiteList = {
                "/api/v1/auth/login",
                "/api/v1/users/profile",
                "/api/v1/restaurants/markers",
                "/actuator",
                "/h2-console",
                "/favicon",
                "/swagger",
                "/v3/api-docs",
                "/api.json",
        };
        RandomGenerator aDefault = RandomGenerator.getDefault();
        int i = aDefault.nextInt(0, whiteList.length - 1);
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest(HttpMethod.GET.name(), whiteList[i]);

        // when
        boolean result = authenticationFilter.shouldNotFilter(httpServletRequest);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void preflight_요청에_해당하면_필터는_동작하지_않는다() {
        // given
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest(HttpMethod.OPTIONS.name(), "requestURI");

        // when
        boolean result = authenticationFilter.shouldNotFilter(httpServletRequest);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 요청_헤더에_액세스_토큰이_존재하지_않으면_예외를_반환환다() throws ServletException, IOException {
        // given
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        // when // then
        assertThatThrownBy(() -> authenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain))
                .isInstanceOf(AuthenticationException.class);

    }

    @Test
    void 요청_헤더에_액세스_토큰_인증_타입이_Bearer가_아니면_예외를_반환환다() throws ServletException, IOException {
        // given
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, "Not Bearer Token");
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        // when // then
        assertThatThrownBy(() -> authenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain))
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    void 요청_정보에_리프레시_토큰_쿠기가_존재하지_않으면_예외를_반환한다() throws ServletException, IOException {
        // given
        String accessToken = "accessToken";
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        willThrow(new ExpiredTokenException(TokenErrorCode.EXPIRED_TOKEN_EXCEPTION)).given(tokenService).validate(accessToken);

        // when // then
        assertThatThrownBy(() -> authenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain))
                .isInstanceOf(AuthenticationException.class);
        verify(tokenService, atMostOnce()).validate(accessToken);
    }

    @Test
    void 만료된_액세스_토큰과_유효한_리프레시_토큰이_있다면_토큰을_재발급한다() throws ServletException, IOException {
        // given
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";
        AuthInfo authInfo = new AuthInfo(1L, Role.USER);

        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        httpServletRequest.setCookies(new Cookie("Refresh-Token", refreshToken));
        MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        TokenResponse tokenResponse = Instancio.create(TokenResponse.class);

        given(tokenService.getAuthInfo(accessToken)).willReturn(authInfo);
        willThrow(new ExpiredTokenException(TokenErrorCode.EXPIRED_TOKEN_EXCEPTION)).given(tokenService).validate(accessToken);
        willDoNothing().given(tokenService).validate(refreshToken);
        given(tokenService.reissueToken(httpServletResponse, refreshToken)).willReturn(tokenResponse);

        // when // then
        authenticationFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);
        assertThat(authContext.getAuthInfo()).isEqualTo(authInfo);
    }

}
