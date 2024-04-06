package kyonggiyo.domain.event.util;

import jakarta.persistence.AttributeConverter;
import kyonggiyo.domain.event.EventReason;

import java.util.Objects;

public class EventReasonConverter implements AttributeConverter<EventReason, String> {

    @Override
    public String convertToDatabaseColumn(EventReason attribute) {
        return Objects.requireNonNull(attribute.getReason());
    }

    @Override
    public EventReason convertToEntityAttribute(String dbData) {
        return EventReason.from(dbData);
    }

}

