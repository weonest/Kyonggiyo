package adapter.out.persistence.event;

import adapter.out.persistence.AdapterTest;
import com.github.f4b6a3.tsid.TsidCreator;
import kyonggiyo.domain.event.EntityType;
import kyonggiyo.domain.event.Event;
import kyonggiyo.domain.event.EventReason;
import kyonggiyo.domain.event.EventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class EventPersistenceAdapterTest extends AdapterTest {

    @Autowired
    private EventPersistenceAdapter eventPersistenceAdapter;

    @Test
    void 커스텀_식별자와_함께_Event를_저장한다() {
        // given
        Long id = TsidCreator.getTsid().toLong();
        Long reviewId = 1L;

        Event event = Event.builder()
                .id(id)
                .entityType(EntityType.REVIEW)
                .eventType(EventType.IMAGE)
                .entityId(reviewId)
                .reason(EventReason.REVIEW_IMAGE_CREATE)
                .build();

        // when
        eventPersistenceAdapter.save(event);

        // then
        Event result = eventPersistenceAdapter.getById(id);
        assertThat(result.getId()).isEqualTo(id);
    }

}
