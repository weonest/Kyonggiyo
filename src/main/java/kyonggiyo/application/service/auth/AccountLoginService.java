package kyonggiyo.application.service.auth;

import kyonggiyo.application.dto.user.UserIdResponse;
import kyonggiyo.application.port.out.auth.FindAccountPort;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountLoginService {

    private final FindAccountPort findAccountPort;
    private final AccountSignInService accountSignInService;

    public UserIdResponse login(Platform platform, String platformId) {
        return findAccountPort.findByPlatformAndPlatformId(platform, platformId)
                .map(account -> UserIdResponse.from(account.getUser()))
                .orElseGet(null);
    }

}
