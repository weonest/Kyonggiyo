package kyonggiyo.application.port.out.auth;

import kyonggiyo.domain.auth.RefreshToken;

public interface SaveRefreshTokenPort {

    void save(RefreshToken refreshToken);

}
