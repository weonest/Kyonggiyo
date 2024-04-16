package kyonggiyo.client.oauth;

import kyonggiyo.domain.auth.Platform;
import lombok.Getter;

@Getter
public abstract class OAuthClient {

    private final Platform platform;

    public OAuthClient(Platform platform) {
        this.platform = platform;
    }

    public abstract String requestToken(String authCode);

    public abstract String requestUserInfo(String accessToken);

}
