package kyonggiyo.application.port.in.user;

import kyonggiyo.application.port.in.user.dto.UserCreateCommand;
import kyonggiyo.domain.auth.Platform;

public interface CreateUserUseCase {

    Platform createUser(UserCreateCommand command);

}
