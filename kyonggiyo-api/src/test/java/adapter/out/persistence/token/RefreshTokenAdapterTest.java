package adapter.out.persistence.token;

import kyonggiyo.domain.auth.RefreshToken;
import kyonggiyo.persistence.token.RefreshTokenAdapter;
import kyonggiyo.persistence.token.RefreshTokenRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        String value = refreshToken.getValue();

        given(refreshTokenRepository.findByValue(value)).willReturn(Optional.of(refreshToken));

        // when
        Optional<RefreshToken> result = refreshTokenAdapter.findByValue(value);

        // then
        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo(refreshToken);
    }

}
