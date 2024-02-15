package kyonggiyo.fixture;

import kyonggiyo.domain.auth.Account;
import org.instancio.Instancio;

import static org.instancio.Select.field;

public class AccountFixtures {

    private AccountFixtures() {
    }

    public static Account generateAccountWithoutUser() {
        return Instancio.of(Account.class)
                .ignore(field(Account::getUser))
                .create();
    }

    public static Account generateAccount() {
        return Instancio.of(Account.class)
                .set(field(Account::getUser), UserFixture.generateUser())
                .create();
    }

}
