package kyonggiyo.application.port.out.user;

import kyonggiyo.domain.user.User;

public interface LoadUserPort {

    User getById(Long id);

}
