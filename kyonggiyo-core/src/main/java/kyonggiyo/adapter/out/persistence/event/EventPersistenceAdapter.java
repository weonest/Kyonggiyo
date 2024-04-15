package kyonggiyo.adapter.out.persistence.event;

import kyonggiyo.application.port.out.event.LoadEventPort;
import kyonggiyo.application.port.out.event.SaveEventPort;
import kyonggiyo.application.port.out.event.UpdateEventPort;
import kyonggiyo.domain.event.Event;
import kyonggiyo.domain.event.EventType;
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
