package kyonggiyo.application.service.oauth;

import kyonggiyo.application.port.out.oauth.RequestOAuthTokenPort;
import kyonggiyo.application.port.out.oauth.RequestOAuthUserInfoPort;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthQueryService {

    private final RequestOAuthTokenPort requestOAuthTokenPort;
    private final RequestOAuthUserInfoPort requestOAuthUserInfoPort;

    public String getProviderId(Platform platform, String authCode) {
        String accessToken = requestOAuthTokenPort.requestToken(platform, authCode);
        return requestOAuthUserInfoPort.requestUserInfo(platform, accessToken);
    }
    
}
