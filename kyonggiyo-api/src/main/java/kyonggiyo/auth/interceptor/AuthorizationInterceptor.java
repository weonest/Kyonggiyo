package kyonggiyo.auth.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kyonggiyo.application.port.in.auth.dto.UserInfo;
import kyonggiyo.auth.Admin;
import kyonggiyo.auth.Auth;
import kyonggiyo.auth.AuthContext;
import kyonggiyo.domain.user.Role;
import kyonggiyo.common.exception.ForbiddenException;
import kyonggiyo.common.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final AuthContext authContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        if (isNotAuthAnnotated(handlerMethod)) {
            return true;
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

    private boolean isNotAuthAnnotated(HandlerMethod method) {
        MethodParameter[] methodParameters = method.getMethodParameters();
        return Arrays.stream(methodParameters)
                .noneMatch(parameter -> parameter.getParameterType().isAssignableFrom(UserInfo.class)
                                        && parameter.hasParameterAnnotation(Auth.class));
    }

    private boolean isAdminMethod(HandlerMethod handlerMethod) {
        return handlerMethod.getMethodAnnotation(Admin.class) != null;
    }

}
