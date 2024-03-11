package kyonggiyo.adapter.out.persistence.user;

import kyonggiyo.application.port.out.user.ExistUserPort;
import kyonggiyo.application.port.out.user.GetUserPort;
import kyonggiyo.application.port.out.user.SaveUserPort;
import kyonggiyo.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements GetUserPort, ExistUserPort, SaveUserPort {

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
