package kyonggiyo.user.service;

import kyonggiyo.user.port.inbound.CreateUserUseCase;
import kyonggiyo.user.dto.UserCreateCommand;
import kyonggiyo.auth.port.outbound.LoadAccountPort;
import kyonggiyo.user.port.outbound.SaveUserPort;
import kyonggiyo.auth.domain.entity.Account;
import kyonggiyo.auth.domain.vo.Platform;
import kyonggiyo.user.domain.entity.User;
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
