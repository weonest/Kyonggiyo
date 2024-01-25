package kyonggiyo.application.port.in.oauth;

import kyonggiyo.adapter.in.web.oauth.dto.LogInResponse;
import kyonggiyo.domain.auth.Platform;

public interface OAuthLoginUseCase {

    LogInResponse login(Platform platform, String code);

}
