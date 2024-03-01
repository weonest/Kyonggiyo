package kyonggiyo.global.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Order(0)
@Component
public class MdcLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getHeader("X-Real-IP");
        MDC.put("request", String.format("%s.%s", request.getRequestURI(), request.getMethod()));
        MDC.put("ip", ip);
        MDC.put("query", "0");
        MDC.put("startTime", String.valueOf(System.currentTimeMillis()));

        filterChain.doFilter(request, response);

        printRequestResult();

        MDC.clear();
    }

    private void printRequestResult() {
        String startTime = MDC.get("startTime");
        String request = MDC.get("request");
        String queryCount = MDC.get("query");
        log.info("요청 정보: {} | 요청 총 소요 시간: {}ms | 총 쿼리 카운트: {} ",
                request,
                (System.currentTimeMillis() - Long.parseLong(startTime)),
                queryCount);
    }

}
