package kyonggiyo.fixture;

import kyonggiyo.domain.auth.Account;
import org.instancio.Instancio;

import static org.instancio.Select.field;

public class AccountFixtures {

    private AccountFixtures() {
    }

    public static Account generateAccountEntity() {
        return Instancio.of(Account.class)
                .set(field(Account::getUser), UserFixture.generateUserEntity())
                .create();
    }

    public static Account generateAccountEntityWithoutUser() {
        return Instancio.of(Account.class)
                .ignore(field(Account::getUser))
                .create();
    }

}
