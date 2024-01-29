package kyonggiyo.application.port.out.auth;

import kyonggiyo.domain.auth.Platform;

public interface RequestOAuthTokenPort {

    String requestToken(Platform platform, String authCode);

}
