package kyonggiyo.application.service.oauth;

import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;
import kyonggiyo.adapter.in.web.auth.dto.LogInResponse;
import kyonggiyo.application.port.in.auth.OAuthLoginUseCase;
import kyonggiyo.application.service.auth.AccountLoginService;
import kyonggiyo.application.service.auth.TokenService;
import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginFacadeService implements OAuthLoginUseCase {

    private final TokenService tokenService;
    private final OAuthQueryService oAuthQueryService;
    private final AccountLoginService accountLoginService;

    @Override
    public LogInResponse login(Platform platform, String code) {
        String platformId = oAuthQueryService.getProviderId(platform, code);
        Account account = accountLoginService.login(platform, platformId);

        if (account.hasNoUser()) {
            return LogInResponse.forDoesntHaveUser(account.getId());
        }

        TokenResponse tokenResponse = tokenService.generateToken(account.getUser());

        return LogInResponse.forHasUser(account.getUserId(), tokenResponse);
    }

}
