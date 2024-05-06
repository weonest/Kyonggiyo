package kyonggiyo.auth.service;

import kyonggiyo.auth.domain.vo.AuthInfo;
import kyonggiyo.auth.domain.entity.RefreshToken;
import kyonggiyo.auth.domain.vo.AccessToken;
import kyonggiyo.domain.user.Role;

public interface TokenManager {

    AccessToken generateAccessToken(Long userId, Role role);

    RefreshToken generateRefreshToken(Long userId, Role role);

    void validate(String token);

    AuthInfo extract(String token);

}
