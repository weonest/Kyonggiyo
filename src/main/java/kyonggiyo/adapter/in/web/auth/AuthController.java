package kyonggiyo.adapter.in.web.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kyonggiyo.adapter.in.web.auth.dto.LogInResponse;
import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;
import kyonggiyo.application.port.in.ReissueTokenUseCase;
import kyonggiyo.application.port.in.auth.OAuthLoginUseCase;
import kyonggiyo.application.port.in.auth.OAuthLogoutUseCase;
import kyonggiyo.application.port.in.auth.ProvideAuthCodeUrlUseCase;
import kyonggiyo.domain.auth.Platform;
import kyonggiyo.global.auth.Auth;
import kyonggiyo.global.auth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final String REFRESH_TOKEN = "Refresh-Token";

    private final ProvideAuthCodeUrlUseCase provideAuthCodeUrlUseCase;
    private final OAuthLoginUseCase oAuthLoginUseCase;
    private final OAuthLogoutUseCase oAuthLogoutUseCase;
    private final ReissueTokenUseCase reissueTokenUseCase;

    @GetMapping("/{platform}")
    public ResponseEntity<Void> getAuthCodeRequestUrl(@PathVariable Platform platform) {
        URI uri = provideAuthCodeUrlUseCase.provideUri(platform);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{platform}/callback")
    public ResponseEntity<LogInResponse> login(@PathVariable Platform platform, @RequestParam String code,
                                               HttpServletResponse httpServletResponse) {
        LogInResponse response = oAuthLoginUseCase.login(platform, code);
        
        setCookie(httpServletResponse, response.token());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@Auth UserInfo userInfo,
                                       HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse) {
        oAuthLogoutUseCase.logout(userInfo);

        removeCookie(httpServletRequest, httpServletResponse);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(HttpServletRequest httpServletRequest,
                                                 HttpServletResponse httpServletResponse) {
        Cookie cookie = findRefreshTokenCookie(httpServletRequest).get();
        TokenResponse tokenResponse = reissueTokenUseCase.reissueToken(cookie.getValue());

        setCookie(httpServletResponse, tokenResponse);

        return ResponseEntity.ok(tokenResponse);
    }

    private Optional<Cookie> findRefreshTokenCookie(HttpServletRequest httpServletRequest) {
        return Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN))
                .findFirst();
    }

    private void setCookie(HttpServletResponse httpServletResponse, TokenResponse tokenResponse) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, tokenResponse.refreshToken())
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(false)
                .maxAge(tokenResponse.refreshTokenMaxAge())
                .build();

        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void removeCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Optional<Cookie> refreshTokenCookie = findRefreshTokenCookie(httpServletRequest);
        if (refreshTokenCookie.isPresent()) {
            Cookie cookie = refreshTokenCookie.get();
            cookie.setMaxAge(0);
            httpServletResponse.addCookie(cookie);
        }
    }

}
