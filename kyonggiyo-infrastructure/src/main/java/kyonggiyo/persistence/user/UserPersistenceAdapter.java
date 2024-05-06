package kyonggiyo.persistence.user;

import kyonggiyo.user.port.outbound.ExistUserPort;
import kyonggiyo.user.port.outbound.LoadUserPort;
import kyonggiyo.user.port.outbound.SaveUserPort;
import kyonggiyo.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoadUserPort, ExistUserPort, SaveUserPort {

    private final UserRepository repository;

    @Override
    public User getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public boolean existByNickname(String nickname) {
        return repository.existByNickname(nickname);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

}
