package kyonggiyo.application.service.auth;

import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;
import kyonggiyo.domain.auth.AccessToken;
import kyonggiyo.domain.auth.RefreshToken;
import kyonggiyo.adapter.out.token.RefreshTokenRepository;
import kyonggiyo.adapter.out.token.TokenManager;
import kyonggiyo.domain.user.User;
import kyonggiyo.global.auth.AuthInfo;
import kyonggiyo.global.auth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenManager tokenManager;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse generateToken(User user) {
        AccessToken accessToken = tokenManager.generateAccessToken(user.getId(), user.getRole());
        RefreshToken refreshToken = tokenManager.generateRefreshToken();

        Duration duration = Duration.ofSeconds(refreshToken.maxAge());
        refreshTokenRepository.save(String.valueOf(user.getId()), refreshToken.value(), duration);

        return new TokenResponse(accessToken.value(), accessToken.maxAge(), refreshToken.value(), refreshToken.maxAge());
    }

    public void deleteRefreshToken(UserInfo userInfo) {
        refreshTokenRepository.deleteByKey(String.valueOf(userInfo.userId()));
    }

    public AuthInfo getAuthInfo(String token) {
        tokenManager.validate(token);
        return tokenManager.extract(token);
    }

}
