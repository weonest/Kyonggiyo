package kyonggiyo.persistence.user;

import kyonggiyo.user.domain.entity.User;

public interface UserRepository {

    User getById(Long id);

    boolean existByNickname(String nickname);

    User save(User user);

}
