package kyonggiyo.domain.event;

import kyonggiyo.application.service.event.ImageCreateEvent;

public enum EventType {

    IMAGE_CREATE(ImageCreateEvent.class),
    ;

    public final Class clazz;

    EventType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

}
