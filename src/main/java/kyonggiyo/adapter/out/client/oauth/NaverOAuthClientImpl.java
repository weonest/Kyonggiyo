package kyonggiyo.adapter.out.client.oauth;

import kyonggiyo.domain.auth.Platform;
import kyonggiyo.global.property.NaverOAuthProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NaverOAuthClientImpl extends OAuthClient {

    private static final String GRANT_TYPE = "authorization_code";

    private final NaverFeignClient naverFeignClient;
    private final NaverOAuthProperties naverOAuthProperties;

    public NaverOAuthClientImpl(NaverFeignClient naverFeignClient, NaverOAuthProperties naverOAuthProperties) {
        super(Platform.NAVER);
        this.naverFeignClient = naverFeignClient;
        this.naverOAuthProperties = naverOAuthProperties;
    }

    @Override
    public String requestToken(String authCode) {
        return naverFeignClient.getAccessToken(getParamMap(authCode))
                .accessToken();
    }

    @Override
    public String requestUserInfo(String accessToken) {
        return naverFeignClient.getUserInfo(convertInToBearer(accessToken))
                .getPlatformId();
    }

    private Map<String, String> getParamMap(String authCode) {
        return Map.of(
                "code", authCode,
                "grant_type", GRANT_TYPE,
                "client_id", naverOAuthProperties.getClientId(),
                "client_secret", naverOAuthProperties.getClientSecret(),
                "redirect_uri", naverOAuthProperties.getUrl().getRedirectUrl()
        );
    }

    private String convertInToBearer(String accessToken) {
        return String.format("Bearer %s", accessToken);
    }

}
