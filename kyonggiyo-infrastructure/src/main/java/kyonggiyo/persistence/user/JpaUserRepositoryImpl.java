package kyonggiyo.persistence.user;

import kyonggiyo.domain.user.User;
import kyonggiyo.global.exception.GlobalErrorCode;
import kyonggiyo.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaUserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public User getById(Long id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(GlobalErrorCode.NOT_FOUND_ENTITY_EXCEPTION));
    }

    @Override
    public boolean existByNickname(String nickname) {
        return jpaRepository.existsByNickname(nickname);
    }

    @Override
    public User save(User user) {
        return jpaRepository.save(user);
    }
    
}
