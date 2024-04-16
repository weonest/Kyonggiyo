package kyonggiyo.persistence.token.entity;

import kyonggiyo.domain.auth.RefreshToken;
import kyonggiyo.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 259200)
public class RefreshTokenEntity {

    @Id
    private Long userId;

    private Role role;

    private String value;

    private long expiresIn;

    @Builder
    private RefreshTokenEntity(Long userId, String value, Role role, long expiresIn) {
        this.userId = userId;
        this.value = value;
        this.role = role;
        this.expiresIn = expiresIn;
    }

    public static RefreshTokenEntity from(RefreshToken refreshToken) {
        return RefreshTokenEntity.builder()
                .userId(refreshToken.getUserId())
                .role(refreshToken.getRole())
                .value(refreshToken.getValue())
                .expiresIn(refreshToken.getExpiresIn())
                .build();
    }

    public RefreshToken toDomain() {
        return RefreshToken.builder()
                .userId(userId)
                .role(role)
                .value(value)
                .expiresIn(expiresIn)
                .build();
    }

}
