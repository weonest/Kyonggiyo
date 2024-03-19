package kyonggiyo.adapter.out.persistence.event;

import kyonggiyo.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<Event, Long> {
}
