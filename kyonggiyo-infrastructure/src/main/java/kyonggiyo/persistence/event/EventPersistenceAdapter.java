package kyonggiyo.persistence.event;

import kyonggiyo.common.entity.event.LoadEventPort;
import kyonggiyo.common.entity.event.SaveEventPort;
import kyonggiyo.common.entity.event.UpdateEventPort;
import kyonggiyo.common.entity.event.Event;
import kyonggiyo.common.entity.event.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventPersistenceAdapter implements SaveEventPort, LoadEventPort, UpdateEventPort {

    private final EventRepository eventRepository;

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.getById(id);
    }

    @Override
    public List<Event> findAllByEventTypeAndStatus(EventType eventType, boolean status) {
        return eventRepository.findAllByEventTypeAndStatus(eventType, status);
    }

    @Override
    public void updateStatusIdIn(List<Long> ids) {
        eventRepository.updateStatusIdIn(ids);
    }

}
