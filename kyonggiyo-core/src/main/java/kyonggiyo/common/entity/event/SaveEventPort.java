package kyonggiyo.common.entity.event;

import kyonggiyo.common.entity.event.Event;

public interface SaveEventPort {

    Event save(Event event);

}
