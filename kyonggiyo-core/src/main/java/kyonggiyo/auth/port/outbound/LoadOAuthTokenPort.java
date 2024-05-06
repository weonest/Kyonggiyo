package kyonggiyo.auth.port.outbound;

import kyonggiyo.auth.domain.vo.Platform;

public interface LoadOAuthTokenPort {

    String requestToken(Platform platform, String authCode);

}
