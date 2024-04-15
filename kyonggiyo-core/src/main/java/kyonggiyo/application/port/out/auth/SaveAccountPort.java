package kyonggiyo.application.port.out.auth;

import kyonggiyo.domain.auth.Account;

public interface SaveAccountPort {

    Account save(Account account);

}
