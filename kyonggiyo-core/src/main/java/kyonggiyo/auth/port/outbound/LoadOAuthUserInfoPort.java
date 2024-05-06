package kyonggiyo.auth.port.outbound;

import kyonggiyo.auth.domain.vo.Platform;

public interface LoadOAuthUserInfoPort {

    String requestUserInfo(Platform platform, String accessToken);

}
