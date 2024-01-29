package kyonggiyo.adapter.out.token;

import java.time.Duration;
import java.util.Optional;

public interface RefreshTokenRepository {

    void save(String key, String value, Duration duration);

    Optional<String> findByKey(String key);

    void deleteByKey(String key);

}
