package kyonggiyo.application.port.out.event;

import kyonggiyo.domain.event.Event;

public interface SaveEventPort {

    Event save(Event event);

}
