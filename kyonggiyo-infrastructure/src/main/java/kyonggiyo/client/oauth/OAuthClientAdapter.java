package kyonggiyo.client.oauth;

import kyonggiyo.auth.port.outbound.LoadOAuthTokenPort;
import kyonggiyo.auth.port.outbound.LoadOAuthUserInfoPort;
import kyonggiyo.auth.domain.vo.Platform;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OAuthClientAdapter implements LoadOAuthTokenPort, LoadOAuthUserInfoPort {

    private final Map<Platform, OAuthClient> oAuthClients;

    public OAuthClientAdapter(Set<OAuthClient> oAuthClient) {
        this.oAuthClients = oAuthClient.stream()
                .collect(Collectors.toUnmodifiableMap(
                        OAuthClient::getPlatform,
                        Function.identity()
                )
        );
    }

    @Override
    public String requestToken(Platform platform, String authCode) {
        return oAuthClients.get(platform).requestToken(authCode);
    }

    @Override
    public String requestUserInfo(Platform platform, String accessToken) {
        return oAuthClients.get(platform).requestUserInfo(accessToken);
    }

}
