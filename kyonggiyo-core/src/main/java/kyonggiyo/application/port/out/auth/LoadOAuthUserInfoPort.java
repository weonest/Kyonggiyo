package kyonggiyo.application.port.out.auth;

import kyonggiyo.domain.auth.Platform;

public interface LoadOAuthUserInfoPort {

    String requestUserInfo(Platform platform, String accessToken);

}
