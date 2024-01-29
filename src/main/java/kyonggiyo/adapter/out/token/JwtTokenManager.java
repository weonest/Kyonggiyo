package kyonggiyo.adapter.out.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kyonggiyo.domain.user.Role;
import kyonggiyo.global.property.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import java.util.random.RandomGenerator;

@Component
@RequiredArgsConstructor
public class JwtTokenManager implements TokenManager {

    private final JwtProperties jwtProperties;

    public String generateAccessToken(Long userId, Role role ) {
        long currentTimeMillis = System.currentTimeMillis();
        int primaryNum = RandomGenerator.getDefault().nextInt();

        Claims claims =  Jwts.claims()
                .add(jwtProperties.claimId(), userId)
                .add(jwtProperties.claimRole(), role)
                .build();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(String.valueOf(primaryNum))
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + jwtProperties.accessTokenExpireTime()))
                .signWith(decodedKey(jwtProperties.secretKey()), SignatureAlgorithm.HS512)
                .compact();
    }

    public RefreshToken generateRefreshToken() {
        String refreshToken = String.valueOf(UUID.randomUUID());
        Duration duration = Duration.ofMillis(jwtProperties.refreshTokenExpireTime());
        return new RefreshToken(refreshToken, duration);
    }

    private static Key decodedKey(String key) {
        return Keys.hmacShaKeyFor(
                key.getBytes(StandardCharsets.UTF_8)
        );
    }

}
