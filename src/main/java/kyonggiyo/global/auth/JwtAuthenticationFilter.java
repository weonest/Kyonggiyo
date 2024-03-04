package kyonggiyo.global.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kyonggiyo.application.service.auth.TokenService;
import kyonggiyo.domain.auth.exception.ExpiredTokenException;
import kyonggiyo.global.exception.AuthenticationException;
import kyonggiyo.global.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Order(2)
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_TYPE = "Bearer";
    private static final String REFRESH_TOKEN = "Refresh-Token";

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
        return Arrays.stream(whiteList).anyMatch(path::startsWith) || request.getMethod().equals(HttpMethod.OPTIONS.name());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveTokenFromHeader(request);
        try {
            tokenService.validate(accessToken);
            AuthInfo authInfo = tokenService.getAuthInfo(accessToken);
            authContext.registerAuthInfo(authInfo);

            filterChain.doFilter(request, response);
        } catch (ExpiredTokenException expiredTokenException) {
            Cookie refreshTokenCookie = resolveTokenFromCookie(request);
            tokenService.validate(refreshTokenCookie.getValue());
            request.getRequestDispatcher("/api/v1/auth/reissue").forward(request, response);
        }
    }

    private String resolveTokenFromHeader(HttpServletRequest request) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return checkType(token.split(" "));
    }

    private String checkType(String[] token) {
        if (token[0].equals(BEARER_TYPE)) {
            return token[1];
        }
        throw new AuthenticationException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION);
    }

    private Cookie resolveTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN))
                    .findFirst()
                    .orElseThrow(() -> new AuthenticationException(GlobalErrorCode.NO_AUTHENTICATION_INFO_EXCEPTION));
        }
        throw new AuthenticationException(GlobalErrorCode.NO_AUTHENTICATION_INFO_EXCEPTION);
    }

}
