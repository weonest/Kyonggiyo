package kyonggiyo.application.service.auth;

import kyonggiyo.auth.domain.vo.AuthInfo;
import kyonggiyo.auth.dto.TokenResponse;
import kyonggiyo.auth.port.outbound.DeleteRefreshTokenPort;
import kyonggiyo.auth.port.outbound.LoadRefreshTokenPort;
import kyonggiyo.auth.port.outbound.SaveRefreshTokenPort;
import kyonggiyo.auth.domain.vo.AccessToken;
import kyonggiyo.auth.domain.entity.RefreshToken;
import kyonggiyo.auth.service.TokenManager;
import kyonggiyo.auth.domain.exception.TokenErrorCode;
import kyonggiyo.domain.user.Role;
import kyonggiyo.domain.user.User;
import kyonggiyo.common.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenManager tokenManager;
    private final SaveRefreshTokenPort saveRefreshTokenPort;
    private final DeleteRefreshTokenPort deleteRefreshTokenPort;
    private final LoadRefreshTokenPort loadRefreshTokenPort;

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
    public void deleteRefreshTokenByUserId(Long userId) {
        deleteRefreshTokenPort.deleteByUserId(userId);
    }

    public void validate(String token) {
        tokenManager.validate(token);
    }

    public AuthInfo getAuthInfo(String token) {
        return tokenManager.extract(token);
    }

    @Transactional
    public TokenResponse reissueToken(Long id, String refreshToken) {
        RefreshToken retrivedRefreshToken = loadRefreshTokenPort.getByUserId(id);

        if (!Objects.equals(refreshToken, retrivedRefreshToken.getValue())){
            throw new AuthenticationException(TokenErrorCode.INVALID_TOKEN_EXCEPTION);
        }

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
