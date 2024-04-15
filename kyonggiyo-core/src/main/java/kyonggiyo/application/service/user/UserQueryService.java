package kyonggiyo.application.service.user;

import kyonggiyo.application.port.in.user.ValidateNicknameUseCase;
import kyonggiyo.application.port.out.user.ExistUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService implements ValidateNicknameUseCase {

    private final ExistUserPort existUserPort;

    @Override
    public boolean existByNickname(String nickname) {
        return existUserPort.existByNickname(nickname);
    }

}
