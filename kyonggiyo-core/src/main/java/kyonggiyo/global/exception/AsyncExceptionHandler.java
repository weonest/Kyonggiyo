package kyonggiyo.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final String LOG_FORMAT = "[ip = %s] 요청 정보 : %s  |  %s";

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.warn(String.format(LOG_FORMAT, MDC.get("ip"), MDC.get("request"), ex.getMessage()), ex);
    }

}
