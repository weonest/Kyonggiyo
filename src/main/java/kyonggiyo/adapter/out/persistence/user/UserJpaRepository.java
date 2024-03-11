package kyonggiyo.adapter.out.persistence.user;

import kyonggiyo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);

}
