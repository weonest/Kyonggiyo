package kyonggiyo.adapter.out.persistence.user;

import kyonggiyo.domain.user.User;

public interface UserRepository {

    User getById(Long id);

    boolean existByNickname(String nickname);

    User save(User user);

}
