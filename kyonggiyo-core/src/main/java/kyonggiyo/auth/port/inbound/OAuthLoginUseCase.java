package kyonggiyo.auth.port.inbound;

import kyonggiyo.auth.dto.LogInResponse;
import kyonggiyo.auth.domain.vo.Platform;

public interface OAuthLoginUseCase {

    LogInResponse login(Platform platform, String code);

}
