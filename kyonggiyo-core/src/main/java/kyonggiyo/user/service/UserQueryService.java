package kyonggiyo.user.service;

import kyonggiyo.user.port.inbound.ValidateNicknameUseCase;
import kyonggiyo.user.port.outbound.ExistUserPort;
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
