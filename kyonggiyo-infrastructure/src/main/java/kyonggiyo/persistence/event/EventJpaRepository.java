package kyonggiyo.persistence.event;

import kyonggiyo.common.entity.event.Event;
import kyonggiyo.common.entity.event.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventJpaRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByPayload_EventTypeAndStatus(EventType eventType, boolean status);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Event e SET e.status = true WHERE e.id IN :ids")
    void updateStatusIdIn(@Param("ids") List<Long> ids);

}
