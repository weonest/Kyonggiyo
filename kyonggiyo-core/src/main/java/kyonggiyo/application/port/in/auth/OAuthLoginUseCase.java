package kyonggiyo.application.port.in.auth;

import kyonggiyo.application.port.in.auth.dto.LogInResponse;
import kyonggiyo.domain.auth.Platform;

public interface OAuthLoginUseCase {

    LogInResponse login(Platform platform, String code);

}
