package kyonggiyo.application.service.oauth;

import kyonggiyo.adapter.in.web.oauth.dto.LogInResponse;
import kyonggiyo.application.dto.user.UserIdResponse;
import kyonggiyo.application.port.in.oauth.OAuthLoginUseCase;
import kyonggiyo.application.service.auth.AccountLoginService;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginFacadeService implements OAuthLoginUseCase {

    private final OAuthQueryService oAuthQueryService;
    private final AccountLoginService accountLoginService;

    @Override
    public LogInResponse login(Platform platform, String code) {
        String platformId = oAuthQueryService.getProviderId(platform, code);
        Long accountId = accountLoginService.login(platform, platformId);
    }
}
