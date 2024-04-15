package kyonggiyo.application.port.out.user;

import kyonggiyo.domain.user.User;

public interface SaveUserPort {

    User save(User user);

}
