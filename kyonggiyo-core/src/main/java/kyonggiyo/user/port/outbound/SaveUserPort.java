package kyonggiyo.user.port.outbound;

import kyonggiyo.user.domain.entity.User;

public interface SaveUserPort {

    User save(User user);

}
