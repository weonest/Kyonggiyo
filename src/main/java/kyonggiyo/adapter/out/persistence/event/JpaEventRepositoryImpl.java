package kyonggiyo.adapter.out.persistence.event;

import kyonggiyo.domain.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaEventRepositoryImpl implements EventRepository {

    private final EventJpaRepository eventJpaRepository;

    @Override
    public Event save(Event event) {
        return eventJpaRepository.save(event);
    }

}
