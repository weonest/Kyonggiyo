package kyonggiyo.adapter.out.token;

import kyonggiyo.domain.auth.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);

    Optional<RefreshToken> findByUserId(Long userId);

    Optional<RefreshToken> findByValue(String token);

    void deleteByUserId(Long userId);

}
