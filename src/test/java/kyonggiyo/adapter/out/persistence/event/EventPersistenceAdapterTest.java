package kyonggiyo.adapter.out.persistence.event;

import kyonggiyo.adapter.out.persistence.AdapterTest;
import kyonggiyo.domain.event.EntityType;
import kyonggiyo.domain.event.Event;
import kyonggiyo.domain.event.EventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EventPersistenceAdapterTest extends AdapterTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void test() {
        Event event = Event.builder()
                .entityData("string")
                .eventType(EventType.IMAGE_CREATE)
                .entityType(EntityType.REVIEW)
                .build();
        eventRepository.save(event);

        eventRepository.findAllByEventTypeAndStatus(EventType.IMAGE_CREATE, false);
    }

}