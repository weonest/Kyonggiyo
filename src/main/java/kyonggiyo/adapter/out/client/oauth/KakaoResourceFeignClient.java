package kyonggiyo.adapter.out.client.oauth;

import kyonggiyo.adapter.out.client.oauth.response.KakaoUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-resource", url = "${oauth.kakao.url.api-url}")
public interface KakaoResourceFeignClient {

    @PostMapping(value = "/v2/user/me", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoUserInfoResponse getUserInfo(@RequestHeader("Authorization") String bearerToken);

}
