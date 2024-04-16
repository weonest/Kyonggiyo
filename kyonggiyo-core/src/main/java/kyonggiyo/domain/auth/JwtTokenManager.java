package kyonggiyo.domain.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kyonggiyo.application.port.in.auth.dto.AuthInfo;
import kyonggiyo.common.property.JwtProperties;
import kyonggiyo.domain.auth.exception.ExpiredTokenException;
import kyonggiyo.domain.auth.exception.InvalidTokenException;
import kyonggiyo.domain.auth.exception.TokenErrorCode;
import kyonggiyo.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.random.RandomGenerator;

@Component
@RequiredArgsConstructor
public class JwtTokenManager implements TokenManager {

    private final JwtProperties jwtProperties;

    @Override
    public AccessToken generateAccessToken(Long userId, Role role) {
        long currentTimeMillis = System.currentTimeMillis();
        int primaryNum = RandomGenerator.getDefault().nextInt();
        long expiresIn = currentTimeMillis + (jwtProperties.accessTokenExpireTime() * 1000L);

        Claims claims = Jwts.claims()
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

        return new AccessToken(accessToken, jwtProperties.accessTokenExpireTime());
    }

    @Override
    public RefreshToken generateRefreshToken(Long userId, Role role) {
        long currentTimeMillis = System.currentTimeMillis();
        int primaryNum = RandomGenerator.getDefault().nextInt();
        long expiresIn = currentTimeMillis + (jwtProperties.refreshTokenExpireTime() * 1000L);

        String refreshToken = Jwts.builder()
                .setIssuer(String.valueOf(primaryNum))
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(expiresIn))
                .signWith(decodedKey(jwtProperties.secretKey()), SignatureAlgorithm.HS512)
                .compact();

        return RefreshToken.builder()
                .userId(userId)
                .role(role)
                .value(refreshToken)
                .expiresIn(jwtProperties.refreshTokenExpireTime())
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
        String claims = token.split("\\.")[1];
        byte[] claimsBytes = Decoders.BASE64URL.decode(claims);
        String decodedClaims = new String(claimsBytes, StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(decodedClaims);
        return new AuthInfo(jsonObject.getLong("id"), Role.valueOf(jsonObject.get("role").toString()));
    }

    private static Key decodedKey(String key) {
        return Keys.hmacShaKeyFor(
                key.getBytes(StandardCharsets.UTF_8)
        );
    }

}
