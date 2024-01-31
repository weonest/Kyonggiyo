package kyonggiyo.global.auth;

public record UserInfo(
        Long userId
) {

    public static UserInfo from(AuthInfo authInfo) {
        return new UserInfo(authInfo.userId());
    }

}
