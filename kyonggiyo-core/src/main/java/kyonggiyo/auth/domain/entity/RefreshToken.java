package kyonggiyo.auth.domain.entity;

import kyonggiyo.domain.user.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

    @Id
    private Long userId;

    private Role role;

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
