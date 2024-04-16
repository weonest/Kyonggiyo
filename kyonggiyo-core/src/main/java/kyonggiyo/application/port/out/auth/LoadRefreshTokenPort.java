package kyonggiyo.application.port.out.auth;

import kyonggiyo.domain.auth.RefreshToken;

public interface LoadRefreshTokenPort {

    RefreshToken getByUserId(Long userId);

}
