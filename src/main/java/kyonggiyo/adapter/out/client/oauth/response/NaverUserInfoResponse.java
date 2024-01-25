package kyonggiyo.adapter.out.client.oauth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverUserInfoResponse implements OAuthUserInfoResponse {

    private String id;

    @Override
    public String getPlatformId() {
        return id;
    }

}
