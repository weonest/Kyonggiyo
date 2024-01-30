package kyonggiyo.application.service.auth;

import kyonggiyo.domain.auth.Platform;
import kyonggiyo.global.property.KakaoOAuthProperties;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Getter
@Component
public class KakaoAuthCodeRequestUrlProvider extends AuthCodeRequestUrlProvider{

    private static final String AUTHORIZATION_URL = "https://kauth.kakao.com/oauth/authorize";

    private final KakaoOAuthProperties kakaoOAuthProperties;

    public KakaoAuthCodeRequestUrlProvider(KakaoOAuthProperties kakaoOAuthProperties) {
        super(Platform.KAKAO);
        this.kakaoOAuthProperties = kakaoOAuthProperties;
    }

    @Override
    public URI provideUri() {
        return UriComponentsBuilder
                .fromUriString(AUTHORIZATION_URL)
                .queryParam("client_id", kakaoOAuthProperties.getClientId())
                .queryParam("redirect_uri", kakaoOAuthProperties.getUrl().getRedirectUrl())
                .queryParam("response_type", kakaoOAuthProperties.getResponseType())
                .build().toUri();
    }

}
