package kyonggiyo.adapter.out.persistence.account;

import kyonggiyo.application.port.out.auth.FindAccountPort;
import kyonggiyo.application.port.out.auth.SaveAccountPort;
import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements FindAccountPort, SaveAccountPort {

    private final AccountRepository repository;

    @Override
    public Optional<Account> findByPlatformAndPlatformId(Platform platform, String platformId) {
        return repository.findByPlatformAndPlatformId(platform, platformId);
    }

    @Override
    public void save(Account account) {
        repository.save(account);
    }
}
