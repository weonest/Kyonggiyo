package kyonggiyo.domain.auth;

import kyonggiyo.domain.user.Role;
import kyonggiyo.global.auth.AuthInfo;

public interface TokenManager {

    AccessToken generateAccessToken(Long userId, Role role);

    RefreshToken generateRefreshToken(Long userId, Role role);

    void validate(String token);

    AuthInfo extract(String token);

}
