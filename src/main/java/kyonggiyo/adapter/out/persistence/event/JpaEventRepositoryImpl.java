package kyonggiyo.adapter.out.persistence.event;

import kyonggiyo.domain.event.Event;
import kyonggiyo.domain.event.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaEventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;

    @Override
    public Event save(Event event) {
        return eventJpaRepository.save(event);
    }

    @Override
    public List<Event> findAllByEventTypeAndStatus(EventType eventType, boolean status) {
        return eventJpaRepository.findAllByEventTypeAndStatus(eventType, status);
    }

    @Override
    public void updateStatusIdIn(List<Long> ids) {
        eventJpaRepository.updateStatusIdIn(ids);
    }

}
