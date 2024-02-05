package kyonggiyo.adapter.out.persistence.account;

import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findById(Long accountId);

    Optional<Account> findByPlatformAndPlatformId(Platform platform, String platformId);

    Account save(Account account);

}
