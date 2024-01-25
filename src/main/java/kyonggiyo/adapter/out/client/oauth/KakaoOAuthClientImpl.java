package kyonggiyo.adapter.out.client.oauth;

import kyonggiyo.domain.auth.Platform;
import kyonggiyo.global.property.KakaoOAuthProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KakaoOAuthClientImpl extends OAuthClient {

    private static final String GRANT_TYPE = "authorization_code";

    private final KakaoFeignClient kakaoFeignClient;
    private final KakaoOAuthProperties kakaoOAuthProperties;

    public KakaoOAuthClientImpl(KakaoFeignClient kakaoFeignClient, KakaoOAuthProperties kakaoOAuthProperties) {
        super(Platform.KAKAO);
        this.kakaoFeignClient = kakaoFeignClient;
        this.kakaoOAuthProperties = kakaoOAuthProperties;
    }

    @Override
    public String requestToken(String authCode) {
        return kakaoFeignClient.getAccessToken(getParamMap(authCode))
                .accessToken();
    }

    @Override
    public String requestUserInfo(String accessToken) {
        return kakaoFeignClient.getUserInfo(convertInToBearer(accessToken))
                .getPlatformId();
    }

    private Map<String, String> getParamMap(String authCode) {
        return Map.of(
                "code", authCode,
                "grant_type", GRANT_TYPE,
                "client_id", kakaoOAuthProperties.getClientId(),
                "redirect_uri", kakaoOAuthProperties.getUrl().getRedirectUrl()
        );
    }

    private String convertInToBearer(String accessToken) {
        return String.format("Bearer %s", accessToken);
    }


}
