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
@Table(name = "review_events")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEvent extends BaseEntity implements Persistable<Long> {

    @Id
    private Long id;

    @Embedded
    private ReviewEventPayload payload;

    private boolean status = false;

    @Builder
    private ReviewEvent(Long id,
                        Long entityId,
                        EventCommand command,
                        String attribute) {
        this.id = id;
        this.payload = ReviewEventPayload.builder()
                .entityId(entityId)
                .command(command)
                .attribute(attribute)
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
