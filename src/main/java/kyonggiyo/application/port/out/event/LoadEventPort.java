package kyonggiyo.application.port.out.event;

import kyonggiyo.domain.event.Event;
import kyonggiyo.domain.event.EventType;

import java.util.List;

public interface LoadEventPort {

    Event getById(Long id);

    List<Event> findAllByEventTypeAndStatus(EventType eventType, boolean status);

}
