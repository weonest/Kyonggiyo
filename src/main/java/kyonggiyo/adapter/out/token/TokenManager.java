package kyonggiyo.adapter.out.token;

import kyonggiyo.domain.user.Role;

public interface TokenManager {

    String generateAccessToken(Long userId, Role role);

    RefreshToken generateRefreshToken();

}
