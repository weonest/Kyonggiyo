package kyonggiyo.adapter.out.token;

import kyonggiyo.application.port.out.auth.DeleteRefreshTokenPort;
import kyonggiyo.application.port.out.auth.FindRefreshTokenByValuePort;
import kyonggiyo.application.port.out.auth.SaveRefreshTokenPort;
import kyonggiyo.domain.auth.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenAdapter implements SaveRefreshTokenPort, FindRefreshTokenByValuePort,
        DeleteRefreshTokenPort {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void save(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByValue(String token) {
        return refreshTokenRepository.findByValue(token);
    }

    @Override
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

}
