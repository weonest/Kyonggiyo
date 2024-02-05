package kyonggiyo.application.service.auth;

import kyonggiyo.application.port.out.auth.FindAccountPort;
import kyonggiyo.application.port.out.auth.SaveAccountPort;
import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountLoginService {

    private final FindAccountPort findAccountPort;
    private final AccountSignUpService accountSignUpService;

    public Account login(Platform platform, String platformId) {
        return findAccountPort.findByPlatformAndPlatformId(platform, platformId)
                .orElseGet(() -> accountSignUpService.save(platform, platformId));
    }

}
