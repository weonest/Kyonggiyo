package kyonggiyo.auth.port.outbound;

import kyonggiyo.auth.domain.entity.Account;

public interface SaveAccountPort {

    Account save(Account account);

}
