package kyonggiyo.persistence.user;

import kyonggiyo.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);

}
