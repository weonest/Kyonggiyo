package kyonggiyo.global.logging;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MdcTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> threadContext = MDC.getCopyOfContextMap();
        return () -> {
            MDC.setContextMap(threadContext);
            runnable.run();
        };
    }

}
