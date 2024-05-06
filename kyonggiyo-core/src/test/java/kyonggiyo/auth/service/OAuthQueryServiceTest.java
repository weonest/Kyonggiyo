package kyonggiyo.auth.service;

import kyonggiyo.auth.port.outbound.LoadOAuthTokenPort;
import kyonggiyo.auth.port.outbound.LoadOAuthUserInfoPort;
import kyonggiyo.ServiceTest;
import kyonggiyo.auth.domain.vo.Platform;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = OAuthQueryService.class)
class OAuthQueryServiceTest extends ServiceTest {

    @Autowired
    private OAuthQueryService oAuthQueryService;

    @MockBean
    private LoadOAuthTokenPort loadOAuthTokenPort;

    @MockBean
    private LoadOAuthUserInfoPort loadOAuthUserInfoPort;

    @Test
    void 로그인_플랫폼에_맞는_플랫폼_아이디를_반환한다() {
        // given
        Platform platform = Instancio.of(Platform.class).create();
        String authCode = "authCode";
        String accessToken = "accessToken";
        String providerId = "providerId";

        given(loadOAuthTokenPort.requestToken(platform, authCode)).willReturn(accessToken);
        given(loadOAuthUserInfoPort.requestUserInfo(platform, accessToken)).willReturn(providerId);

        // when
        String result = oAuthQueryService.getProviderId(platform, authCode);

        // then
        assertThat(result).isEqualTo(providerId);
    }

}
