package kyonggiyo.application.service.auth;

import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;
import kyonggiyo.application.port.in.auth.ReissueTokenUseCase;
import kyonggiyo.application.port.out.auth.DeleteRefreshTokenPort;
import kyonggiyo.application.port.out.auth.FindRefreshTokenByValuePort;
import kyonggiyo.application.port.out.auth.SaveRefreshTokenPort;
import kyonggiyo.domain.auth.AccessToken;
import kyonggiyo.domain.auth.RefreshToken;
import kyonggiyo.domain.auth.TokenManager;
import kyonggiyo.domain.auth.exception.ExpiredTokenException;
import kyonggiyo.domain.auth.exception.TokenErrorCode;
import kyonggiyo.domain.user.Role;
import kyonggiyo.domain.user.User;
import kyonggiyo.global.auth.AuthInfo;
import kyonggiyo.global.auth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService implements ReissueTokenUseCase {

    private final TokenManager tokenManager;
    private final SaveRefreshTokenPort saveRefreshTokenPort;
    private final DeleteRefreshTokenPort deleteRefreshTokenPort;
    private final FindRefreshTokenByValuePort findRefreshTokenByValuePort;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TokenResponse generateToken(User user) {
        AccessToken accessToken = tokenManager.generateAccessToken(user.getId(), user.getRole());
        RefreshToken refreshToken = tokenManager.generateRefreshToken(user.getId(), user.getRole());

        saveRefreshTokenPort.save(refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken.value())
                .accessTokenMaxAge(accessToken.expiresIn())
                .refreshToken(refreshToken.getValue())
                .refreshTokenMaxAge(refreshToken.getExpiresIn())
                .build();
    }

    @Transactional
    public void deleteRefreshToken(UserInfo userInfo) {
        deleteRefreshTokenPort.deleteByUserId(userInfo.userId());
    }

    public void validate(String token) {
        tokenManager.validate(token);
    }

    public AuthInfo getAuthInfo(String token) {
        return tokenManager.extract(token);
    }

    @Override
    @Transactional
    public TokenResponse reissueToken(String refreshToken) {
        RefreshToken retrivedRefreshToken = findRefreshTokenByValuePort.findByValue(refreshToken)
                .orElseThrow(() -> new ExpiredTokenException(TokenErrorCode.EXPIRED_TOKEN_EXCEPTION));

        Long userId = retrivedRefreshToken.getUserId();
        Role userRole = retrivedRefreshToken.getRole();

        AccessToken newAccessToken = tokenManager.generateAccessToken(userId, userRole);
        RefreshToken newRefreshToken = tokenManager.generateRefreshToken(userId, userRole);

        saveRefreshTokenPort.save(newRefreshToken);

        return TokenResponse.builder()
                .accessToken(newAccessToken.value())
                .accessTokenMaxAge(newAccessToken.expiresIn())
                .refreshToken(newRefreshToken.getValue())
                .refreshTokenMaxAge(newRefreshToken.getExpiresIn())
                .build();
    }

}
