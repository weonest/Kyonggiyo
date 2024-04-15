package kyonggiyo.application.port.in.auth;

import kyonggiyo.global.auth.UserInfo;

public interface OAuthLogoutUseCase {

    void logout(UserInfo userInfo);

}
