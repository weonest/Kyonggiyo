package kyonggiyo.application.service.user;

import kyonggiyo.adapter.in.web.auth.dto.CreateUserRequest;
import kyonggiyo.application.port.in.user.CreateUserUseCase;
import kyonggiyo.application.port.out.auth.FindAccountPort;
import kyonggiyo.application.port.out.user.SaveUserPort;
import kyonggiyo.domain.auth.Account;
import kyonggiyo.domain.auth.Platform;
import kyonggiyo.domain.user.User;
import kyonggiyo.global.exception.GlobalErrorCode;
import kyonggiyo.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService implements CreateUserUseCase {

    private final FindAccountPort findAccountPort;
    private final SaveUserPort saveUserPort;

    @Override
    public Platform createUser(CreateUserRequest createUserRequest) {
        Account account = findAccountPort.findById(createUserRequest.accountId())
                .orElseThrow(() -> new NotFoundException(GlobalErrorCode.NOT_FOUND_ENTITY_EXCEPTION));

        User user = createUserRequest.toEntity();
        account.registerUser(saveUserPort.save(user));
        return account.getPlatform();
    }

}
