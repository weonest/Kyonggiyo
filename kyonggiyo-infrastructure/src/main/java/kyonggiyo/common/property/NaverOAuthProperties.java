package kyonggiyo.common.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "oauth.naver")
public class NaverOAuthProperties {

    private final String responseType;
    private final String clientId;
    private final String clientSecret;
    private final String state;
    private final Url url;

    @RequiredArgsConstructor
    public static final class Url {

        private static String NAVER_AUTHORIZE_URI = "/oauth2.0/authorize";
        private static String NAVER_REQUEST_TOKEN_URI = "/oauth2.0/token";
        private static String NAVER_REQUEST_INFO_URI = "/v1/nid/me";

        private final String authUrl;
        private final String apiUrl;
        private final String redirectUrl;

        public String getAuthorizeUrl() {
            return authUrl + NAVER_AUTHORIZE_URI;
        }

        public String getRequestTokenUrl() {
            return authUrl + NAVER_REQUEST_TOKEN_URI;
        }

        public String getRequestInfoUrl() {
            return apiUrl + NAVER_REQUEST_INFO_URI;
        }

        public String getRedirectUrl() {
            return redirectUrl;
        }
    }

}
