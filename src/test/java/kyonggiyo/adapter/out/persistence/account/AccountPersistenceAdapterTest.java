package kyonggiyo.adapter.out.persistence.account;

import kyonggiyo.adapter.out.persistence.AdapterTest;
import kyonggiyo.domain.auth.Account;
import kyonggiyo.fixture.AccountFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Import({AccountPersistenceAdapter.class, JpaAccountRepositoryImpl.class})
class AccountPersistenceAdapterTest extends AdapterTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountPersistenceAdapter accountPersistenceAdapter;

    @Test
    void 식별자를_통해_DB에서_Account를_조회할_수_있다() {
        // given
        Account account = AccountFixtures.generateAccountEntityWithoutUser();
        accountRepository.save(account);

        entityManager.flush();
        entityManager.clear();

        // when
        Optional<Account> result = accountPersistenceAdapter.findById(account.getId());

        // then
        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo(account);
    }

    @Test
    void 플랫폼과_플랫폼id를_통해_DB에서_Account를_조회할_수_있다() {
        // given
        Account account = AccountFixtures.generateAccountEntityWithoutUser();
        accountRepository.save(account);

        entityManager.flush();
        entityManager.clear();

        // when
        Optional<Account> result = accountPersistenceAdapter.findByPlatformAndPlatformId(account.getPlatform(), account.getPlatformId());

        // then
        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo(account);
    }

}
