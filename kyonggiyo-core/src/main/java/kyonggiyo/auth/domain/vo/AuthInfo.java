package kyonggiyo.auth.domain.vo;

import kyonggiyo.user.domain.vo.Role;

public record AuthInfo(
        Long userId,
        Role role
) {
}

