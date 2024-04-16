package kyonggiyo.persistence.account;

import kyonggiyo.application.port.out.auth.LoadAccountPort;
import kyonggiyo.application.port.out.auth.SaveAccountPort;
import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements LoadAccountPort, SaveAccountPort {

    private final AccountRepository repository;

    @Override
    public Optional<Account> findById(Long accountId) {
        return repository.findById(accountId);
    }

    @Override
    public Optional<Account> findByPlatformAndPlatformId(Platform platform, String platformId) {
        return repository.findByPlatformAndPlatformId(platform, platformId);
    }

    @Override
    public Account save(Account account) {
        return repository.save(account);
    }

}
