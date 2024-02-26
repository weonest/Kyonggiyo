package kyonggiyo.adapter.out.persistence.user;

import kyonggiyo.domain.user.User;

public interface UserRepository {

    User getById(Long id);

    User save(User user);

}
