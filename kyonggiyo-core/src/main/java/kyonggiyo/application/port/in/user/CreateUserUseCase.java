package kyonggiyo.application.port.in.user;

import kyonggiyo.application.port.in.user.dto.UserCreateCommand;
import kyonggiyo.auth.domain.vo.Platform;

public interface CreateUserUseCase {

    Platform createUser(UserCreateCommand command);

}
