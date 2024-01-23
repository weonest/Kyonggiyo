package kyonggiyo.adapter.in.web.oauth;

import kyonggiyo.application.port.in.oauth.ProvideAuthCodeUrlUseCase;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth2")
public class OAuthController {

    private final ProvideAuthCodeUrlUseCase provideAuthCodeUrlUseCase;

    @GetMapping("/{platform}")
    public ResponseEntity<Void> getAuthCodeRequestUrl(@PathVariable Platform platform) {
        URI uri = provideAuthCodeUrlUseCase.provideUri(platform);
        return ResponseEntity.created(uri).build();
    }

}
