package kyonggiyo.domain.auth;

import kyonggiyo.application.port.in.auth.dto.AuthInfo;
import kyonggiyo.common.property.JwtProperties;
import kyonggiyo.domain.user.Role;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JwtTokenManagerTest {

    private JwtProperties jwtProperties = new JwtProperties(
            "id",
            "role",
            "secretKeysecretKeysecretKeysecretKeysecretKeysecretKeysecretKeysecretKeysecretKeysecretKey",
            10,
            30
    );

    private JwtTokenManager jwtTokenManager = new JwtTokenManager(jwtProperties);

    @Test
    void 유저_아이디와_권한_정보로_액세스_토큰을_생성할_수_있다() {
        // given
        Long userId = 0L;
        Role role = Role.USER;

        // when
        AccessToken accessToken = jwtTokenManager.generateAccessToken(userId, role);

        // then
        assertThat(accessToken).isNotNull();
    }

    @Test
    void 유저_아이디와_권한_정보로_리프레시_토큰을_생성할_수_있다() {
        // given
        Long userId = 1L;
        Role role = Role.USER;

        // when
        RefreshToken refreshToken = jwtTokenManager.generateRefreshToken(userId, role);

        // then
        assertThat(refreshToken).isNotNull();
    }

    @Test
    void 유효한_토큰을_검증할_수_있다() {
        // given
        Long userId = 1L;
        Role role = Role.USER;
        AccessToken accessToken = jwtTokenManager.generateAccessToken(userId, role);

        // then
        assertThatNoException().isThrownBy(() -> jwtTokenManager.validate(accessToken.value()));
    }

    @Test
    void 유효한_토큰에서_인증_정보를_추출할_수_있다() {
        // given
        Long userId = 1L;
        Role role = Role.USER;
        AccessToken accessToken = jwtTokenManager.generateAccessToken(userId, role);

        // when
        AuthInfo authInfo = jwtTokenManager.extract(accessToken.value());

        assertThat(userId).isEqualTo(authInfo.userId());
        assertThat(role).isEqualTo(authInfo.role());
    }

}
