package kyonggiyo.auth;

import kyonggiyo.auth.domain.vo.AuthInfo;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class AuthContext {

    private AuthInfo authInfo;

    public void registerAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }

}

