package kyonggiyo.auth.port.outbound;

import kyonggiyo.auth.domain.entity.RefreshToken;

public interface LoadRefreshTokenPort {

    RefreshToken getByUserId(Long userId);

}
