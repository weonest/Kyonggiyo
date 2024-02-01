package kyonggiyo.application.port.out.auth;

import kyonggiyo.domain.auth.RefreshToken;

import java.util.Optional;

public interface FindRefreshTokenByValuePort {

    Optional<RefreshToken> findByValue(String token);

}
