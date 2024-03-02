package kyonggiyo.application.port.in.user;

import kyonggiyo.adapter.in.web.auth.dto.UserCreateRequst;
import kyonggiyo.domain.auth.Platform;

public interface CreateUserUseCase {

    Platform createUser(UserCreateRequst userCreateRequst);

}
