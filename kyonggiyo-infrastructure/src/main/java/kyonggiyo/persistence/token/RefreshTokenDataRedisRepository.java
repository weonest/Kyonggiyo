package kyonggiyo.persistence.token;

import kyonggiyo.persistence.token.entity.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenDataRedisRepository extends CrudRepository<RefreshTokenEntity, Long> {

}
