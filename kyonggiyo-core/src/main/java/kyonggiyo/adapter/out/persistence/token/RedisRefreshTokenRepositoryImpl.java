package kyonggiyo.adapter.out.persistence.token;

import kyonggiyo.domain.auth.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenDataRedisRepository redisRepository;

    @Override
    public void save(RefreshToken refreshToken) {
        redisRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByUserId(Long userId) {
        return redisRepository.findById(userId);
    }

    @Override
    public Optional<RefreshToken> findByValue(String token) {
        return redisRepository.findByValue(token);
    }

    @Override
    public void deleteByUserId(Long userId) {
        redisRepository.deleteById(userId);
    }

}
