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
@Table(name = "Image_events")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageEvent extends BaseEntity implements Persistable<Long> {

    @Id
    private Long id;

    @Embedded
    private ImageEventPayload payload;

    private boolean status = false;

    @Builder
    private ImageEvent(Long id,
                       EntityType entityType,
                       Long entityId,
                       EventCommand reason,
                       String imageUrls) {
        this.id = id;
        this.payload = ImageEventPayload.builder()
                .entityType(entityType)
                .entityId(entityId)
                .reason(reason)
                .imageUrls(imageUrls)
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
