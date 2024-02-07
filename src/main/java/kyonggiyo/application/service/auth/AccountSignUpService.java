package kyonggiyo.application.service.auth;

import kyonggiyo.application.port.out.auth.SaveAccountPort;
import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountSignUpService {

    private final SaveAccountPort saveAccountPort;

    @Transactional
    public Account signup(Platform platform, String platformId) {
        Account account = new Account(platform, platformId);
        return saveAccountPort.save(account);
    }

}
