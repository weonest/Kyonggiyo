package kyonggiyo.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kyonggiyo.application.port.in.auth.dto.AuthInfo;
import kyonggiyo.application.port.in.auth.dto.TokenResponse;
import kyonggiyo.application.service.auth.TokenService;
import kyonggiyo.auth.AuthContext;
import kyonggiyo.domain.auth.exception.ExpiredTokenException;
import kyonggiyo.common.exception.AuthenticationException;
import kyonggiyo.common.exception.GlobalErrorCode;
import kyonggiyo.common.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Order(2)
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_TYPE = "Bearer";

    private final AuthContext authContext;
    private final TokenService tokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
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
        String path = request.getRequestURI();
        return Arrays.stream(whiteList).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveTokenFromHeader(request);
        AuthInfo authInfo = tokenService.getAuthInfo(accessToken);
        try {
            tokenService.validate(accessToken);
        } catch (ExpiredTokenException expiredTokenException) {
            Cookie refreshTokenCookie = CookieUtils.getRefreshTokenCookie(request);
            tokenService.validate(refreshTokenCookie.getValue());
            TokenResponse tokenResponse = tokenService.reissueToken(authInfo.userId(), refreshTokenCookie.getValue());
            renewToken(response, tokenResponse);
        }
        authContext.registerAuthInfo(authInfo);
        filterChain.doFilter(request, response);
    }

    private String resolveTokenFromHeader(HttpServletRequest request) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || token.isBlank() || token.isEmpty()) {
            throw new AuthenticationException(GlobalErrorCode.NO_AUTHENTICATION_INFO_EXCEPTION);
        }
        return checkType(token.split(" "));
    }

    private String checkType(String[] token) {
        if (token[0].equals(BEARER_TYPE)) {
            return token[1];
        }
        throw new AuthenticationException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION);
    }

    private void renewToken(HttpServletResponse response, TokenResponse tokenResponse) {
        response.addHeader(HttpHeaders.AUTHORIZATION, tokenResponse.accessToken());
        CookieUtils.setCookie(response, tokenResponse);
    }

}
