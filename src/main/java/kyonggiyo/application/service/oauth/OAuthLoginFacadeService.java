package kyonggiyo.application.service.oauth;

import kyonggiyo.adapter.in.web.oauth.dto.LogInResponse;
import kyonggiyo.application.port.in.oauth.OAuthLoginUseCase;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginFacadeService implements OAuthLoginUseCase {

    private final OAuthQueryService oAuthQueryService;

    @Override
    public LogInResponse login(Platform platform, String code) {
        String platformId = oAuthQueryService.getProviderId(platform, code);

    }
}
