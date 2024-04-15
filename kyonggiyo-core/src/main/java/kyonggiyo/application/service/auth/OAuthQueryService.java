package kyonggiyo.application.service.auth;

import kyonggiyo.application.port.out.auth.LoadOAuthTokenPort;
import kyonggiyo.application.port.out.auth.LoadOAuthUserInfoPort;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthQueryService {

    private final LoadOAuthTokenPort loadOAuthTokenPort;
    private final LoadOAuthUserInfoPort loadOAuthUserInfoPort;

    public String getProviderId(Platform platform, String authCode) {
        String accessToken = loadOAuthTokenPort.requestToken(platform, authCode);
        return loadOAuthUserInfoPort.requestUserInfo(platform, accessToken);
    }
    
}
