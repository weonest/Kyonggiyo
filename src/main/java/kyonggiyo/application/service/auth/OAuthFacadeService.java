package kyonggiyo.application.service.auth;

import kyonggiyo.adapter.in.web.auth.dto.LogInResponse;
import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;
import kyonggiyo.application.port.in.auth.OAuthLoginUseCase;
import kyonggiyo.application.port.in.auth.OAuthLogoutUseCase;
import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;
import kyonggiyo.global.auth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthFacadeService implements OAuthLoginUseCase, OAuthLogoutUseCase {

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

    @Override
    public void logout(UserInfo userInfo) {
        tokenService.deleteRefreshToken(userInfo);
    }

}
