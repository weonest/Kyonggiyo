package kyonggiyo.application.service.auth;

import kyonggiyo.auth.port.inbound.OAuthLoginUseCase;
import kyonggiyo.auth.port.inbound.OAuthLogoutUseCase;
import kyonggiyo.auth.dto.LogInResponse;
import kyonggiyo.auth.dto.TokenResponse;
import kyonggiyo.auth.domain.entity.Account;
import kyonggiyo.auth.domain.vo.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthFacadeService implements OAuthLoginUseCase, OAuthLogoutUseCase {

    private final TokenService tokenService;
    private final OAuthQueryService oAuthQueryService;
    private final AccountLoginService accountLoginService;

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
    public void logout(Long userId) {
        tokenService.deleteRefreshTokenByUserId(userId);
    }

}
