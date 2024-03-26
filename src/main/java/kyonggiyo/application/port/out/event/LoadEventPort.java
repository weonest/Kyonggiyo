package kyonggiyo.application.port.out.event;

import kyonggiyo.domain.event.Event;
import kyonggiyo.domain.event.EventType;

import java.util.List;

public interface LoadEventPort {

    List<Event> findAllByEventTypeAndStatus(EventType eventType, boolean status);

}
