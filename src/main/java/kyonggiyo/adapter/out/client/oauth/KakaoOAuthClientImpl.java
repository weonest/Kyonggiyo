package kyonggiyo.adapter.out.client.oauth;

import kyonggiyo.domain.auth.Platform;
import kyonggiyo.global.property.KakaoOAuthProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KakaoOAuthClientImpl extends OAuthClient {

    private static final String GRANT_TYPE = "authorization_code";

    private final KakaoOAuthProperties kakaoOAuthProperties;
    private final KakaoTokenFeignClient kakaoTokenFeignClient;
    private final KakaoResourceFeignClient kakaoResourceFeignClient;

    public KakaoOAuthClientImpl(KakaoOAuthProperties kakaoOAuthProperties,
                                KakaoTokenFeignClient kakaoTokenFeignClient,
                                KakaoResourceFeignClient kakaoResourceFeignClient) {

        super(Platform.KAKAO);
        this.kakaoOAuthProperties = kakaoOAuthProperties;
        this.kakaoTokenFeignClient = kakaoTokenFeignClient;
        this.kakaoResourceFeignClient = kakaoResourceFeignClient;
    }

    @Override
    public String requestToken(String authCode) {
        return kakaoTokenFeignClient.getAccessToken(getParamMap(authCode))
                .accessToken();
    }

    @Override
    public String requestUserInfo(String accessToken) {
        return String.valueOf(kakaoResourceFeignClient.getUserInfo(convertInToBearer(accessToken))
                .getPlatformId());
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
