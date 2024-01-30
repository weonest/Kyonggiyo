package kyonggiyo.application.service.auth;

import kyonggiyo.domain.auth.Platform;
import kyonggiyo.global.property.NaverOAuthProperties;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Getter
@Component
public class NaverAuthCodeRequestUrlProvider extends AuthCodeRequestUrlProvider {

    private static final String AUTHORIZATION_URL = "https://nid.naver.com/oauth2.0/authorize";

    private final NaverOAuthProperties naverOAuthProperties;

    public NaverAuthCodeRequestUrlProvider(NaverOAuthProperties naverOAuthProperties) {
        super(Platform.NAVER);
        this.naverOAuthProperties = naverOAuthProperties;
    }

    @Override
    public URI provideUri() {
        return UriComponentsBuilder
                .fromUriString(naverOAuthProperties.getUrl().getAuthorizeUrl())
                .queryParam("state", naverOAuthProperties.getState())
                .queryParam("client_id", naverOAuthProperties.getClientId())
                .queryParam("redirect_uri", naverOAuthProperties.getUrl().getRedirectUrl())
                .queryParam("response_type", naverOAuthProperties.getResponseType())
                .build().toUri();
    }

}
