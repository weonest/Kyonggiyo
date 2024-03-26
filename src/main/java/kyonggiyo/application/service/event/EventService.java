package kyonggiyo.application.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kyonggiyo.application.port.out.event.SaveEventPort;
import kyonggiyo.domain.event.EntityType;
import kyonggiyo.domain.event.Event;
import kyonggiyo.domain.event.EventType;
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
    public Event createEvent(Event event) {
        return saveEventPort.save(event);
    }

    @Transactional
    public void successEvent(Event event) {
        event.successEvent();
    }

    public String convertEventToJson(T event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.warn("이벤트를 JSON으로 변환 과정에서 예외가 발생하였습니다.");
            throw new RuntimeException(e);
        }
    }

    public T convertDataToEntity(Event event) {
        String entityData = event.getEntityData();
        EventType eventType = event.getEventType();
        try {
            return (T) objectMapper.readValue(entityData, eventType.getClazz());
        } catch (JsonProcessingException e) {
            log.warn("JSON을 엔티티로 변환하는 작업에서 예외가 발생하였습니다.");
            throw new RuntimeException(e);
        }
    }

}
