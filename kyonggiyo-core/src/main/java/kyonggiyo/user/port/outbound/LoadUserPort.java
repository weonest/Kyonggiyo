package kyonggiyo.user.port.outbound;

import kyonggiyo.user.domain.entity.User;

public interface LoadUserPort {

    User getById(Long id);

}
