package kyonggiyo.domain.event;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kyonggiyo.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "events")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    private Long entityId;

    private String entityData;

    private boolean status = false;

    @Builder
    private Event(Long id,
                  EventType eventType,
                  EntityType entityType,
                  Long entityId,
                  String entityData) {
        this.id = id;
        this.eventType = eventType;
        this.entityType = entityType;
        this.entityId = entityId;
        this.entityData = entityData;
    }

    public void successEvent() {
        this.status = true;
    }

}
