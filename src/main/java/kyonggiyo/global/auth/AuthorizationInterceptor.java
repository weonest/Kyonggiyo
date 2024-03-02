package kyonggiyo.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kyonggiyo.domain.user.Role;
import kyonggiyo.global.exception.ForbiddenException;
import kyonggiyo.global.exception.GlobalErrorCode;
import kyonggiyo.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final AuthContext authContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            throw new NotFoundException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION, "존재하지 않는 핸들러입니다.");
        }

        Role userRole = authContext.getAuthInfo().role();

        if (isAdminMethod(handlerMethod)) {
            if (!Objects.equals(Role.ADMIN, userRole)) {
                throw new ForbiddenException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION, "관리자에게 문의하세요.");
            }
            return true;
        }

        return true;
    }

    private boolean isAdminMethod(HandlerMethod handlerMethod) {
        return handlerMethod.getMethodAnnotation(Admin.class) != null;
    }

}
