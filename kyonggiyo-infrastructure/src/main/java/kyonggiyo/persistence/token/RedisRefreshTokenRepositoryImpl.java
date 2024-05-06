package kyonggiyo.persistence.token;

import kyonggiyo.auth.domain.entity.RefreshToken;
import kyonggiyo.auth.domain.exception.ExpiredTokenException;
import kyonggiyo.auth.domain.exception.TokenErrorCode;
import kyonggiyo.persistence.token.entity.RefreshTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenDataRedisRepository redisRepository;

    @Override
    public void save(RefreshToken refreshToken) {
        redisRepository.save(RefreshTokenEntity.from(refreshToken));
    }

    @Override
    public RefreshToken getByUserId(Long userId) {
        RefreshTokenEntity refreshTokenEntity = redisRepository.findById(userId)
                .orElseThrow(() -> new ExpiredTokenException(TokenErrorCode.EXPIRED_TOKEN_EXCEPTION));
        return refreshTokenEntity.toDomain();
    }

    @Override
    public void deleteByUserId(Long userId) {
        redisRepository.deleteById(userId);
    }

}
