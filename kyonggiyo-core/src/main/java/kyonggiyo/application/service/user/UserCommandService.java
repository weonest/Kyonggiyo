package kyonggiyo.application.service.user;

import kyonggiyo.application.port.in.user.CreateUserUseCase;
import kyonggiyo.application.port.in.user.dto.UserCreateCommand;
import kyonggiyo.application.port.out.auth.LoadAccountPort;
import kyonggiyo.application.port.out.user.SaveUserPort;
import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;
import kyonggiyo.domain.user.User;
import kyonggiyo.common.exception.GlobalErrorCode;
import kyonggiyo.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService implements CreateUserUseCase {

    private final LoadAccountPort loadAccountPort;
    private final SaveUserPort saveUserPort;

    @Override
    public Platform createUser(UserCreateCommand command) {
        Account account = loadAccountPort.findById(command.accountId())
                .orElseThrow(() -> new NotFoundException(GlobalErrorCode.NOT_FOUND_ENTITY_EXCEPTION));

        User user = command.toEntity();
        account.registerUser(saveUserPort.save(user));
        return account.getPlatform();
    }

}
