package kyonggiyo.persistence.account;

import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByPlatformAndPlatformId(Platform platform, String platformId);

}
