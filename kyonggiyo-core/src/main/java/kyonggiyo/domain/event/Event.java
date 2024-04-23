package kyonggiyo.domain.event;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kyonggiyo.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Getter
@Entity
@Table(name = "events")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity implements Persistable<Long> {

    @Id
    private Long id;

    @Embedded
    private EventPayload payload;

    private boolean status = false;

    @Builder
    private Event(Long id,
                  EventType eventType,
                  EntityType entityType,
                  Long entityId,
                  EventCommand command) {
        this.id = id;
        this.payload = EventPayload.builder()
                .eventType(eventType)
                .entityType(entityType)
                .entityId(entityId)
                .command(command)
                .build();
    }

    public void successEvent() {
        this.status = true;
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() != null;
    }

}
