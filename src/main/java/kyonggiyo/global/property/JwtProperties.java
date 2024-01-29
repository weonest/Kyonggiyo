package kyonggiyo.global.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String claimId,
        String claimRole,
        String secretKey,
        long accessTokenExpireTime,
        long refreshTokenExpireTime
) {
}