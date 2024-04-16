package kyonggiyo.domain.auth;

import kyonggiyo.application.port.in.auth.dto.AuthInfo;
import kyonggiyo.domain.user.Role;

public interface TokenManager {

    AccessToken generateAccessToken(Long userId, Role role);

    RefreshToken generateRefreshToken(Long userId, Role role);

    void validate(String token);

    AuthInfo extract(String token);

}
