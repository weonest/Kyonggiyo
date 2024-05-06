package kyonggiyo.persistence.token;

import kyonggiyo.auth.domain.entity.RefreshToken;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RefreshTokenAdapterTest {

    @InjectMocks
    private RefreshTokenAdapter refreshTokenAdapter;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    void 리프레시_토큰의_토큰값으로_조회할_수_있다() {
        // given
        RefreshToken refreshToken = Instancio.create(RefreshToken.class);
        Long userId = refreshToken.getUserId();

        given(refreshTokenRepository.getByUserId(userId)).willReturn(refreshToken);

        // when
        RefreshToken result = refreshTokenAdapter.getByUserId(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
    }

}
