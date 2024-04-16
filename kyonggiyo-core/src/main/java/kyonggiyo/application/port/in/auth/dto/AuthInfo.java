package kyonggiyo.application.port.in.auth.dto;

import kyonggiyo.domain.user.Role;

public record AuthInfo(
        Long userId,
        Role role
) {
}

