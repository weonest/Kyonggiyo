package kyonggiyo.adapter.out.client.oauth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponse implements OAuthUserInfoResponse {

    private Long id;

    @Override
    public String getPlatformId() {
        return String.valueOf(id);
    }

}
