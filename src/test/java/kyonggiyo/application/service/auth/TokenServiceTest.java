package kyonggiyo.application.service.auth;

import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;
import kyonggiyo.domain.auth.TokenManager;
import kyonggiyo.application.port.out.auth.DeleteRefreshTokenPort;
import kyonggiyo.application.port.out.auth.FindRefreshTokenByValuePort;
import kyonggiyo.application.port.out.auth.SaveRefreshTokenPort;
import kyonggiyo.application.service.ServiceTest;
import kyonggiyo.domain.auth.AccessToken;
import kyonggiyo.domain.auth.RefreshToken;
import kyonggiyo.domain.user.User;
import kyonggiyo.fixture.UserFixtures;
import kyonggiyo.global.auth.AuthInfo;
import kyonggiyo.global.auth.UserInfo;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = TokenService.class)
class TokenServiceTest extends ServiceTest {

    @Autowired
    private TokenService tokenService;

    @MockBean
    private TokenManager tokenManager;

    @MockBean
    private SaveRefreshTokenPort saveRefreshTokenPort;

    @MockBean
    private DeleteRefreshTokenPort deleteRefreshTokenPort;

    @MockBean
    private FindRefreshTokenByValuePort findRefreshTokenByValuePort;

    @Test
    void 유저의_정보를_통해_토큰을_생성하고_반환할_수_있다() {
        // given
        User user = UserFixtures.generateUserEntity();
        AccessToken accessToken = Instancio.create(AccessToken.class);
        RefreshToken refreshToken = Instancio.create(RefreshToken.class);
        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken(accessToken.value())
                .accessTokenMaxAge(accessToken.expiresIn())
                .refreshToken(refreshToken.getValue())
                .refreshTokenMaxAge(refreshToken.getExpiresIn())
                .build();

        given(tokenManager.generateAccessToken(user.getId(), user.getRole())).willReturn(accessToken);
        given(tokenManager.generateRefreshToken(user.getId(), user.getRole())).willReturn(refreshToken);
        willDoNothing().given(saveRefreshTokenPort).save(refreshToken);

        // when
        TokenResponse result = tokenService.generateToken(user);

        // then
        assertThat(result).isEqualTo(tokenResponse);
    }

    @Test
    void 인증된_유저_정보를_통해_토큰을_삭제할_수_있다() {
        // given
        UserInfo userInfo = Instancio.create(UserInfo.class);

        willDoNothing().given(deleteRefreshTokenPort).deleteByUserId(userInfo.userId());

        // when
        tokenService.deleteRefreshToken(userInfo);

        // then
        verify(deleteRefreshTokenPort, atMostOnce()).deleteByUserId(userInfo.userId());
    }

    @Test
    void 토큰의_유효성_검사를_진행할_수_있다() {
        // given
        String token = "Valid Token";

        willDoNothing().given(tokenManager).validate(token);

        // when
        tokenService.validate(token);

        // then
        verify(tokenManager, atMostOnce()).validate(token);
    }

    @Test
    void 토큰에서_인증_정보를_가져올_수_있다() {
        // given
        String token = "Valid Token";
        AuthInfo authInfo = Instancio.create(AuthInfo.class);

        given(tokenManager.extract(token)).willReturn(authInfo);

        // when
        AuthInfo result = tokenService.getAuthInfo(token);

        // then
        assertThat(result).isEqualTo(authInfo);
    }

    @Test
    void 리프레시_토큰을_통해서_토큰을_재발급_할_수_있다() {
        String authenticatedRefreshToken = "Valid token";
        AccessToken accessToken = Instancio.create(AccessToken.class);
        RefreshToken refreshToken = Instancio.create(RefreshToken.class);
        TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken(accessToken.value())
                .accessTokenMaxAge(accessToken.expiresIn())
                .refreshToken(refreshToken.getValue())
                .refreshTokenMaxAge(refreshToken.getExpiresIn())
                .build();

        given(findRefreshTokenByValuePort.findByValue(authenticatedRefreshToken))
                .willReturn(Optional.of(refreshToken));
        given(tokenManager.generateAccessToken(refreshToken.getUserId(), refreshToken.getRole())).willReturn(accessToken);
        given(tokenManager.generateRefreshToken(refreshToken.getUserId(), refreshToken.getRole())).willReturn(refreshToken);
        willDoNothing().given(saveRefreshTokenPort).save(refreshToken);

        // when
        TokenResponse result = tokenService.reissueToken(authenticatedRefreshToken);

        // then
        assertThat(result).isEqualTo(tokenResponse);
    }

}
