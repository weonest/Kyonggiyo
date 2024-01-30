package kyonggiyo.application.service.auth;

import kyonggiyo.application.port.out.auth.RequestOAuthTokenPort;
import kyonggiyo.application.port.out.auth.RequestOAuthUserInfoPort;
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
