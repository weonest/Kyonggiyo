package kyonggiyo.auth.port.outbound;

import kyonggiyo.auth.domain.entity.Account;
import kyonggiyo.auth.domain.vo.Platform;

import java.util.Optional;

public interface LoadAccountPort {

    Optional<Account> findById(Long accountId);

    Optional<Account> findByPlatformAndPlatformId(Platform platform, String platformId);

}
