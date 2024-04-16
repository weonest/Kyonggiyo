package kyonggiyo.api.adapter.controller.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kyonggiyo.application.port.in.auth.OAuthLoginUseCase;
import kyonggiyo.application.port.in.auth.OAuthLogoutUseCase;
import kyonggiyo.application.port.in.auth.ProvideAuthCodeUrlUseCase;
import kyonggiyo.application.port.in.auth.dto.LogInResponse;
import kyonggiyo.auth.Auth;
import kyonggiyo.domain.auth.Platform;
import kyonggiyo.application.port.in.auth.dto.UserInfo;
import kyonggiyo.common.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final String REFRESH_TOKEN = "Refresh-Token";

    private final ProvideAuthCodeUrlUseCase provideAuthCodeUrlUseCase;
    private final OAuthLoginUseCase oAuthLoginUseCase;
    private final OAuthLogoutUseCase oAuthLogoutUseCase;

    @GetMapping("/login/{platform}")
    public ResponseEntity<Void> authCodeRequestUrlProvide(@PathVariable Platform platform) {
        URI uri = provideAuthCodeUrlUseCase.provideUri(platform);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/login/{platform}/callback")
    public ResponseEntity<LogInResponse> login(@PathVariable Platform platform, @RequestParam String code,
                                               HttpServletResponse httpServletResponse) {
        LogInResponse response = oAuthLoginUseCase.login(platform, code);

        if (Objects.nonNull(response.token())) {
            CookieUtils.setCookie(httpServletResponse, response.token());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@Auth UserInfo userInfo,
                                       HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse) {
        oAuthLogoutUseCase.logout(userInfo.userId());

        CookieUtils.removeCookie(httpServletRequest, httpServletResponse);

        return ResponseEntity.noContent().build();
    }

}
