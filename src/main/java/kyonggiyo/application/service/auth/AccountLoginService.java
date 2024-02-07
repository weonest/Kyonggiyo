package kyonggiyo.application.service.auth;

import kyonggiyo.application.port.out.auth.FindAccountPort;
import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountLoginService {

    private final FindAccountPort findAccountPort;
    private final AccountSignUpService accountSignUpService;

    @Transactional(readOnly = true)
    public Account login(Platform platform, String platformId) {
        return findAccountPort.findByPlatformAndPlatformId(platform, platformId)
                .orElseGet(() -> accountSignUpService.signup(platform, platformId));
    }

}
