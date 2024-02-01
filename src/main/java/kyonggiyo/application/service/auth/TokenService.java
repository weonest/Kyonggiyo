package kyonggiyo.application.service.auth;

import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;
import kyonggiyo.adapter.out.token.TokenManager;
import kyonggiyo.application.port.in.ReissueTokenUseCase;
import kyonggiyo.application.port.out.auth.DeleteRefreshTokenPort;
import kyonggiyo.application.port.out.auth.FindRefreshTokenByValuePort;
import kyonggiyo.application.port.out.auth.SaveRefreshTokenPort;
import kyonggiyo.domain.auth.AccessToken;
import kyonggiyo.domain.auth.RefreshToken;
import kyonggiyo.domain.auth.exception.ExpiredTokenException;
import kyonggiyo.domain.auth.exception.TokenErrorCode;
import kyonggiyo.domain.user.Role;
import kyonggiyo.domain.user.User;
import kyonggiyo.global.auth.AuthInfo;
import kyonggiyo.global.auth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService implements ReissueTokenUseCase {

    private final TokenManager tokenManager;
    private final SaveRefreshTokenPort saveRefreshTokenPort;
    private final DeleteRefreshTokenPort deleteRefreshTokenPort;
    private final FindRefreshTokenByValuePort findRefreshTokenByValuePort;

    public TokenResponse generateToken(User user) {
        AccessToken accessToken = tokenManager.generateAccessToken(user.getId(), user.getRole());
        RefreshToken refreshToken = tokenManager.generateRefreshToken(user.getId(), user.getRole());

        saveRefreshTokenPort.save(refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken.value())
                .accessTokenMaxAge(accessToken.expiresIn())
                .refreshToken(refreshToken.getValue())
                .refreshTokenKey(refreshToken.getUserId())
                .refreshTokenMaxAge(refreshToken.getExpiresIn())
                .build();
    }

    public void deleteRefreshToken(UserInfo userInfo) {
        deleteRefreshTokenPort.deleteByUserId(userInfo.userId());
    }

    public AuthInfo getAuthInfo(String token) {
        tokenManager.validate(token);
        return tokenManager.extract(token);
    }

    @Override
    public TokenResponse reissueToken(String refreshToken) {
        RefreshToken retrivedRefreshToken = findRefreshTokenByValuePort.findByValue(refreshToken)
                .orElseThrow(() -> new ExpiredTokenException(TokenErrorCode.EXPIRED_JWT_EXCEPTION));

        Long userId = retrivedRefreshToken.getUserId();
        Role userRole = retrivedRefreshToken.getRole();

        AccessToken newAccessToken = tokenManager.generateAccessToken(userId, userRole);
        RefreshToken newRefreshToken = tokenManager.generateRefreshToken(userId, userRole);

        return TokenResponse.builder()
                .accessToken(newAccessToken.value())
                .accessTokenMaxAge(newAccessToken.expiresIn())
                .refreshToken(newRefreshToken.getValue())
                .refreshTokenKey(newRefreshToken.getUserId())
                .refreshTokenMaxAge(newRefreshToken.getExpiresIn())
                .build();
    }

}
