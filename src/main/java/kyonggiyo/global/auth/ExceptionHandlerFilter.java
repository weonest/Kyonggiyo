package kyonggiyo.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kyonggiyo.global.exception.AuthenticationException;
import kyonggiyo.global.exception.ErrorCode;
import kyonggiyo.global.exception.GlobalErrorCode;
import kyonggiyo.global.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthenticationException authenticationException) {
            log.warn("인증 예외 발생", authenticationException);
            setErrorResponse(response, authenticationException.getErrorCode());
        } catch (Exception exception) {
            log.warn("요청 에러 발생", exception);
            setErrorResponse(response, GlobalErrorCode.INVALID_REQUEST_EXCEPTION);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        try {
            String json = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(json);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

}
