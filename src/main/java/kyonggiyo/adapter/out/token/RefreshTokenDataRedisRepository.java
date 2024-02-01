package kyonggiyo.adapter.out.token;

import kyonggiyo.domain.auth.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenDataRedisRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByValue(String value);

}
