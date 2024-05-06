package kyonggiyo.application.service.auth;

import kyonggiyo.auth.port.outbound.LoadAccountPort;
import kyonggiyo.auth.domain.entity.Account;
import kyonggiyo.auth.domain.vo.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountLoginService {

    private final LoadAccountPort loadAccountPort;
    private final AccountSignUpService accountSignUpService;
    
    @Transactional(readOnly = true)
    public Account login(Platform platform, String platformId) {
        return loadAccountPort.findByPlatformAndPlatformId(platform, platformId)
                .orElseGet(() -> accountSignUpService.signup(platform, platformId));
    }

}
