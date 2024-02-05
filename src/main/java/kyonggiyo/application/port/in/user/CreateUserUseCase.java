package kyonggiyo.application.port.in.user;

import kyonggiyo.adapter.in.web.auth.dto.CreateUserRequest;
import kyonggiyo.domain.auth.Platform;

public interface CreateUserUseCase {

    Platform createUser(CreateUserRequest createUserRequest);

}
