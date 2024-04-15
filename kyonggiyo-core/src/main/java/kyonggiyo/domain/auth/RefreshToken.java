package kyonggiyo.domain.auth;

import kyonggiyo.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 259200)
public class RefreshToken {

    @Id
    private Long userId;

    private Role role;

    @Indexed
    private String value;

    private long expiresIn;

    @Builder
    private RefreshToken(Long userId, String value, Role role, long expiresIn) {
        this.userId = userId;
        this.value = value;
        this.role = role;
        this.expiresIn = expiresIn;
    }

}
