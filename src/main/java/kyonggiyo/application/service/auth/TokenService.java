package kyonggiyo.application.service.auth;

import kyonggiyo.adapter.in.web.auth.dto.TokenResponse;
import kyonggiyo.adapter.out.token.RefreshToken;
import kyonggiyo.adapter.out.token.RefreshTokenRepository;
import kyonggiyo.adapter.out.token.TokenManager;
import kyonggiyo.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenManager tokenManager;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse generateToken(User user) {
        String accessToken = tokenManager.generateAccessToken(user.getId(), user.getRole());
        RefreshToken refreshToken = tokenManager.generateRefreshToken();

        refreshTokenRepository.save(String.valueOf(user.getId()), refreshToken.value(), refreshToken.duration());

        return new TokenResponse(accessToken, refreshToken.value());
    }



}
