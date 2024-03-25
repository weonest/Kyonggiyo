package kyonggiyo.application.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kyonggiyo.application.port.out.event.SaveEventPort;
import kyonggiyo.domain.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventService<T> {

    private final ObjectMapper objectMapper;
    private final SaveEventPort saveEventPort;

    @Transactional
    public void createEvent(Event event) {
        String entityData = eventToJson(event);
        Event.builder()
                .eventType(event)
    }

    private String eventToJson(T event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.warn("이벤트 변환 과정에서 예외가 발생하였습니다.");
            throw new RuntimeException(e);
        }
    }


}
