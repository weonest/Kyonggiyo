package kyonggiyo.global.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kyonggiyo.application.service.auth.TokenService;
import kyonggiyo.domain.user.Role;
import kyonggiyo.global.exception.ForbiddenException;
import kyonggiyo.global.exception.GlobalErrorCode;
import kyonggiyo.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationInterceptor implements HandlerInterceptor {

    private final AuthContext authContext;
    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            throw new NotFoundException(GlobalErrorCode.INVALID_REQUEST_ERROR);
        }

        if (isNotAuthAnnotated(handlerMethod)) {
            return true;
        }

        String accessToken = getValueFromHeader(request, AUTHORIZATION);

        if (accessToken == null && !isAuthRequired(handlerMethod)) {
            return true;
        }

        AuthInfo authInfo = tokenService.getAuthInfo(accessToken);
        authContext.registerAuthInfo(authInfo);

        if (isAdminMethod(handlerMethod)) {
            Role methodRole = handlerMethod.getMethodAnnotation(Admin.class).role();
            Role userRole = authInfo.role();
            if (!Objects.equals(methodRole, userRole)) {
                throw new ForbiddenException(GlobalErrorCode.INVALID_REQUEST_ERROR);
            }
            return true;
        }

        return true;
    }

    private boolean isNotAuthAnnotated(HandlerMethod handlerMethod) {
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        return Arrays.stream(methodParameters)
                .noneMatch(parameter -> parameter.getParameterType().isAssignableFrom(UserInfo.class)
                                        && parameter.hasParameterAnnotation(Auth.class));
    }

    private boolean isAuthRequired(HandlerMethod handlerMethod) {
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        return Arrays.stream(methodParameters)
                .map(parameter -> parameter.getParameterAnnotation(Auth.class))
                .filter(Objects::nonNull)
                .findFirst()
                .filter(Auth::required)
                .isPresent();
    }

    private boolean isAdminMethod(HandlerMethod handlerMethod) {
        return handlerMethod.getMethodAnnotation(Admin.class) != null;
    }

    private String getValueFromHeader(HttpServletRequest request, String headerName) {
        return request.getHeader(headerName);
    }

}
