package kyonggiyo.persistence.event;

import kyonggiyo.common.entity.event.Event;
import kyonggiyo.common.entity.event.EventType;

import java.util.List;

public interface EventRepository {

    Event save(Event event);

    Event getById(Long id);

    List<Event> findAllByEventTypeAndStatus(EventType eventType, boolean status);

    void updateStatusIdIn(List<Long> ids);

}
