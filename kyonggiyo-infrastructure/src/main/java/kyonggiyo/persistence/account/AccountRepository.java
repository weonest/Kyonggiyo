package kyonggiyo.persistence.account;

import kyonggiyo.auth.domain.entity.Account;
import kyonggiyo.auth.domain.vo.Platform;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findById(Long accountId);

    Optional<Account> findByPlatformAndPlatformId(Platform platform, String platformId);

    Account save(Account account);

}
