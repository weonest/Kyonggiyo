package kyonggiyo.adapter.out.persistence.user;

import kyonggiyo.domain.user.User;

public interface UserRepository {

    User save(User user);

}
