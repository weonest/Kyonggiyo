package kyonggiyo.adapter.out.client.oauth;

import kyonggiyo.adapter.out.client.oauth.response.OAuthTokenResponse;
import kyonggiyo.adapter.out.client.oauth.response.OAuthUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "naver-auth", url = "${oauth.naver.url.auth-url}")
public interface NaverFeignClient {

    @PostMapping(value = "/oauth2.0/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    OAuthTokenResponse getAccessToken(Map<String, String> params);

    @PostMapping(value = "/v1/nid/me", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    OAuthUserInfoResponse getUserInfo(@RequestHeader("Authorization") String bearerToken);

}
