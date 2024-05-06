package kyonggiyo.common.entity.event.util;

import jakarta.persistence.AttributeConverter;
import kyonggiyo.common.entity.event.EventCommand;

import java.util.Objects;

public class EventCommandConverter implements AttributeConverter<EventCommand, String> {

    @Override
    public String convertToDatabaseColumn(EventCommand attribute) {
        return Objects.requireNonNull(attribute.getCommand());
    }

    @Override
    public EventCommand convertToEntityAttribute(String dbData) {
        return EventCommand.from(dbData);
    }

}

