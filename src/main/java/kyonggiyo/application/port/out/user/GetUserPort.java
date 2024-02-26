package kyonggiyo.application.port.out.user;

import kyonggiyo.domain.user.User;

public interface GetUserPort {

    User getById(Long id);

}
