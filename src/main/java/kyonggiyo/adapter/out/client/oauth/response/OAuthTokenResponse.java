package kyonggiyo.adapter.out.client.oauth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OAuthTokenResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
