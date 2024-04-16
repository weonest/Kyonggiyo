package kyonggiyo.persistence.token;

import kyonggiyo.domain.auth.RefreshToken;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);

    RefreshToken getByUserId(Long userId);

    void deleteByUserId(Long userId);

}
