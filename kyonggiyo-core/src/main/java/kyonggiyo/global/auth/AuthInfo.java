package kyonggiyo.global.auth;

import kyonggiyo.domain.user.Role;

public record AuthInfo(
        Long userId,
        Role role
) {
}

