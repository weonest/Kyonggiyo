package kyonggiyo.auth.domain.vo;

import kyonggiyo.user.domain.vo.Role;

public record UserInfo(
        Long userId,
        Role role
) {

    public static UserInfo from(AuthInfo authInfo) {
        return new UserInfo(authInfo.userId(), authInfo.role());
    }

}
