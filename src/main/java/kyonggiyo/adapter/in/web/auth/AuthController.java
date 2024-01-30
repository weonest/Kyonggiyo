package kyonggiyo.adapter.in.web.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kyonggiyo.adapter.in.web.auth.dto.LogInResponse;
import kyonggiyo.application.port.in.auth.OAuthLoginUseCase;
import kyonggiyo.application.port.in.auth.ProvideAuthCodeUrlUseCase;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth2")
public class AuthController {

    private static final String REFRESH_TOKEN = "Refresh-Token";

    private final ProvideAuthCodeUrlUseCase provideAuthCodeUrlUseCase;
    private final OAuthLoginUseCase oAuthLoginUseCase;

    @GetMapping("/{platform}")
    public ResponseEntity<Void> getAuthCodeRequestUrl(@PathVariable Platform platform) {
        URI uri = provideAuthCodeUrlUseCase.provideUri(platform);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{platform}/callback")
    public ResponseEntity<LogInResponse> login(@PathVariable Platform platform, @RequestParam String code,
                                               HttpServletResponse httpResponse) {
        LogInResponse response = oAuthLoginUseCase.login(platform, code);
        
        setCookie(httpResponse, response.token().refreshToken());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public void logout() {

    }

    private void setCookie(HttpServletResponse httpResponse, String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN, refreshToken);
        cookie.setHttpOnly(true);
        // https 적용 후 secure 추가
        httpResponse.addCookie(cookie);
    }

}
