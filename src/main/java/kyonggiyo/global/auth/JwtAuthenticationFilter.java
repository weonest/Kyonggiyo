package kyonggiyo.global.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kyonggiyo.application.service.auth.TokenService;
import kyonggiyo.domain.auth.exception.ExpiredTokenException;
import kyonggiyo.domain.auth.exception.TokenErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Order(0)
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_TYPE = "Bearer ";
    private static final String REFRESH_TOKEN = "Refresh-Token";

    private final AuthContext authContext;
    private final TokenService tokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return super.shouldNotFilter(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> accessToken = resolveTokenFromHeader(request);
        if (accessToken.isPresent()) {
            AuthInfo authInfo = tokenService.getAuthInfo(accessToken.get());
            authContext.registerAuthInfo(authInfo);
            filterChain.doFilter(request, response);
        }

        resolveTokenFromCookie(request);
        request.getRequestDispatcher("/api/v1/auth/reissue").forward(request, response);
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

    private String resolveTokenFromCookie(HttpServletRequest request) {
        Optional<Cookie> refreshTokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN))
                .findFirst();

        if (refreshTokenCookie.isPresent()) {
            return refreshTokenCookie.get().getValue();
        }
        throw new ExpiredTokenException(TokenErrorCode.EXPIRED_JWT_EXCEPTION);
    }

}
