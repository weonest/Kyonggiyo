package kyonggiyo.persistence.token;

import kyonggiyo.application.port.out.auth.DeleteRefreshTokenPort;
import kyonggiyo.application.port.out.auth.LoadRefreshTokenPort;
import kyonggiyo.application.port.out.auth.SaveRefreshTokenPort;
import kyonggiyo.domain.auth.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenAdapter implements SaveRefreshTokenPort, LoadRefreshTokenPort,
        DeleteRefreshTokenPort {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void save(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken getByUserId(Long userId) {
        return refreshTokenRepository.getByUserId(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

}
