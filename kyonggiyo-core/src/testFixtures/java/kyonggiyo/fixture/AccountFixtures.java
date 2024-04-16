package kyonggiyo.fixture;

import kyonggiyo.domain.auth.Account;
import org.instancio.Instancio;
import org.instancio.Select;

public class AccountFixtures {

    private AccountFixtures() {
    }


    public static Account generateAccountEntity() {
        return Instancio.of(Account.class)
                .set(Select.field(Account::getUser), UserFixtures.generateUserEntity())
                .create();
    }

    public static Account generateAccountEntityWithoutUser() {
        return Instancio.of(Account.class)
                .ignore(Select.field(Account::getUser))
                .create();
    }

}
