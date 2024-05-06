package kyonggiyo.common.entity.event;

import kyonggiyo.common.entity.event.Event;
import kyonggiyo.common.entity.event.EventType;

import java.util.List;

public interface LoadEventPort {

    Event getById(Long id);

    List<Event> findAllByEventTypeAndStatus(EventType eventType, boolean status);

}
