package kyonggiyo.adapter.out.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kyonggiyo.domain.auth.AccessToken;
import kyonggiyo.domain.auth.RefreshToken;
import kyonggiyo.domain.auth.exception.ExpiredTokenException;
import kyonggiyo.domain.auth.exception.InvalidTokenException;
import kyonggiyo.domain.auth.exception.TokenErrorCode;
import kyonggiyo.domain.user.Role;
import kyonggiyo.global.auth.AuthInfo;
import kyonggiyo.global.property.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.random.RandomGenerator;

@Component
@RequiredArgsConstructor
public class JwtTokenManager implements TokenManager {

    private final JwtProperties jwtProperties;

    @Override
    public AccessToken generateAccessToken(Long userId, Role role ) {
        long currentTimeMillis = System.currentTimeMillis();
        int primaryNum = RandomGenerator.getDefault().nextInt();
        long expiresIn = (currentTimeMillis / 1000) + jwtProperties.accessTokenExpireTime();

        Claims claims =  Jwts.claims()
                .add(jwtProperties.claimId(), userId)
                .add(jwtProperties.claimRole(), role)
                .build();

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuer(String.valueOf(primaryNum))
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(expiresIn))
                .signWith(decodedKey(jwtProperties.secretKey()), SignatureAlgorithm.HS512)
                .compact();

        return new AccessToken(accessToken, expiresIn);
    }

    @Override
    public RefreshToken generateRefreshToken(Long userId, Role role) {
        long currentTimeMillis = System.currentTimeMillis();
        long expiresIn = (currentTimeMillis / 1000) + jwtProperties.refreshTokenExpireTime();

        String refreshToken = String.valueOf(UUID.randomUUID());

        return RefreshToken.builder()
                .userId(userId)
                .role(role)
                .value(refreshToken)
                .expiresIn(expiresIn)
                .build();
    }

    @Override
    public void validate(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(decodedKey(jwtProperties.secretKey()))
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new ExpiredTokenException(TokenErrorCode.EXPIRED_TOKEN_EXCEPTION);
        } catch (Exception exception) {
            throw new InvalidTokenException(TokenErrorCode.INVALID_TOKEN_EXCEPTION);
        }
    }

    @Override
    public AuthInfo extract(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(decodedKey(jwtProperties.secretKey()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long id = claims.get(jwtProperties.claimId(), Long.class);
        Role role = claims.get(jwtProperties.claimRole(), Role.class);

        return new AuthInfo(id, role);
    }

    private static Key decodedKey(String key) {
        return Keys.hmacShaKeyFor(
                key.getBytes(StandardCharsets.UTF_8)
        );
    }

}
