package kyonggiyo.user.port.inbound;

import kyonggiyo.user.dto.UserCreateCommand;
import kyonggiyo.auth.domain.vo.Platform;

public interface CreateUserUseCase {

    Platform createUser(UserCreateCommand command);

}
