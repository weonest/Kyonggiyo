package kyonggiyo.auth;

import kyonggiyo.application.port.in.auth.dto.AuthInfo;
import kyonggiyo.domain.user.Role;

public record UserInfo(
        Long userId,
        Role role
) {

    public static UserInfo from(AuthInfo authInfo) {
        return new UserInfo(authInfo.userId(), authInfo.role());
    }

}
