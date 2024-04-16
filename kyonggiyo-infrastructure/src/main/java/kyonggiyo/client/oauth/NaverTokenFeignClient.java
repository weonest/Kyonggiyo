package kyonggiyo.client.oauth;

import kyonggiyo.client.oauth.response.OAuthTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "naver-token", url = "https://nid.naver.com")
public interface NaverTokenFeignClient {

    @PostMapping(value = "/oauth2.0/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    OAuthTokenResponse getAccessToken(Map<String, String> params);

}
