package kyonggiyo.adapter.out.persistence.event;

import kyonggiyo.application.port.out.event.SaveEventPort;
import kyonggiyo.domain.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPersistenceAdapter implements SaveEventPort {

    private final EventRepository eventRepository;

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

}
