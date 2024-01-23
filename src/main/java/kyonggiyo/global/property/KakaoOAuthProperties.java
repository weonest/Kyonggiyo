package kyonggiyo.global.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "oauth.kakao")
public class KakaoOAuthProperties {

    private final String responseType;
    private final String clientId;
    private final Url url;

    @RequiredArgsConstructor
    public static final class Url {

        private static String KAKAO_AUTHORIZE_URI = "/oauth/authorize";
        private static String KAKAO_REQUEST_TOKEN_URI = "/oauth/token";
        private static String KAKAO_REQUEST_INFO_URI = "/v2/user/me";

        private final String authUrl;
        private final String apiUrl;
        private final String redirectUrl;

        public String getAuthorizeUrl() {
            return authUrl + KAKAO_AUTHORIZE_URI;
        }

        public String getRequestTokenUrl() {
            return authUrl + KAKAO_REQUEST_TOKEN_URI;
        }

        public String getRequestInfoUrl() {
            return apiUrl + KAKAO_REQUEST_INFO_URI;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }
    }

}
