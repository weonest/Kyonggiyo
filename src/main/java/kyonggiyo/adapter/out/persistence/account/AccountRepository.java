package kyonggiyo.adapter.out.persistence.account;

import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findByPlatformAndPlatformId(Platform platform, String platformId);

    void save(Account Account);

}
