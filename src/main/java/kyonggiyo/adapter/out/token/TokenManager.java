package kyonggiyo.adapter.out.token;

import kyonggiyo.domain.auth.AccessToken;
import kyonggiyo.domain.auth.RefreshToken;
import kyonggiyo.domain.user.Role;
import kyonggiyo.global.auth.AuthInfo;

public interface TokenManager {

    AccessToken generateAccessToken(Long userId, Role role);

    RefreshToken generateRefreshToken();

    void validate(String token);

    AuthInfo extract(String token);

}
