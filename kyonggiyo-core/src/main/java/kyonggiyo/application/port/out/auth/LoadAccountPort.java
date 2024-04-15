package kyonggiyo.application.port.out.auth;

import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;

import java.util.Optional;

public interface LoadAccountPort {

    Optional<Account> findById(Long accountId);

    Optional<Account> findByPlatformAndPlatformId(Platform platform, String platformId);

}
