package kyonggiyo.global.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kyonggiyo.application.service.auth.TokenService;
import kyonggiyo.global.exception.AuthenticationException;
import kyonggiyo.global.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Order(1)
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_TYPE = "Bearer ";
    private static final String REFRESH_TOKEN = "Refresh-Token";

    private final AuthContext authContext;
    private final TokenService tokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] whiteList = {
                "/api/v1/auth/login",
                "/favicon.ico",
                "/h2-console",
        };
        String path = request.getRequestURI();
        return Arrays.stream(whiteList).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> accessToken = resolveTokenFromHeader(request);
        if (accessToken.isPresent()) {
            AuthInfo authInfo = tokenService.getAuthInfo(accessToken.get());
            authContext.registerAuthInfo(authInfo);

            filterChain.doFilter(request, response);
            return;
        }

        Optional<Cookie> refreshTokenCookie = resolveTokenFromCookie(request);
        if (refreshTokenCookie.isPresent()) {
            request.getRequestDispatcher("/api/v1/auth/reissue").forward(request, response);
        }
        throw new AuthenticationException(GlobalErrorCode.NO_AUTHENTICATION_INFO_ERROR);
    }

    private Optional<String> resolveTokenFromHeader(HttpServletRequest request) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(token)) {
            return Optional.empty();
        }
        return checkType(token.split(" "));
    }

    private Optional<String> checkType(String[] token) {
        if (token.length == 2 && token[0].equals(BEARER_TYPE)) {
            return Optional.ofNullable(token[1]);
        }
        return Optional.empty();
    }

    private Optional<Cookie> resolveTokenFromCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN))
                .findFirst();
    }

}
