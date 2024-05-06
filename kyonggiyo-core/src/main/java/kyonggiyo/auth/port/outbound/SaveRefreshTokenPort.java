package kyonggiyo.auth.port.outbound;

import kyonggiyo.auth.domain.entity.RefreshToken;

public interface SaveRefreshTokenPort {

    void save(RefreshToken refreshToken);

}
