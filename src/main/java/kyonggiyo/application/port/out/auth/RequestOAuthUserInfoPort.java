package kyonggiyo.application.port.out.auth;

import kyonggiyo.domain.auth.Platform;

public interface RequestOAuthUserInfoPort {

    String requestUserInfo(Platform platform, String accessToken);

}
