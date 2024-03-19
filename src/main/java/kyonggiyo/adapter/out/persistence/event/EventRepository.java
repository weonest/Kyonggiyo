package kyonggiyo.adapter.out.persistence.event;

import kyonggiyo.domain.event.Event;

public interface EventRepository {

    Event save(Event event);

}
