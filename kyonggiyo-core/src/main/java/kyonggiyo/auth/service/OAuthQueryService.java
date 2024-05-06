package kyonggiyo.auth.service;

import kyonggiyo.auth.port.outbound.LoadOAuthTokenPort;
import kyonggiyo.auth.port.outbound.LoadOAuthUserInfoPort;
import kyonggiyo.auth.domain.vo.Platform;
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
