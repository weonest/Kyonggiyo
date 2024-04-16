package kyonggiyo.persistence.event;

import kyonggiyo.domain.event.Event;
import kyonggiyo.domain.event.EventType;

import java.util.List;

public interface EventRepository {

    Event save(Event event);

    Event getById(Long id);

    List<Event> findAllByEventTypeAndStatus(EventType eventType, boolean status);

    void updateStatusIdIn(List<Long> ids);

}
