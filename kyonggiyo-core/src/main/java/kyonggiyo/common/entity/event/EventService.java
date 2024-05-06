package kyonggiyo.common.entity.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
@RequiredArgsConstructor
public class EventService {

    private final SaveEventPort saveEventPort;
    private final LoadEventPort loadEventPort;

    @Transactional
    public Event createEvent(Event event) {
        return saveEventPort.save(event);
    }

    @Transactional
    public void successEvent(Long id) {
        Event event = loadEventPort.getById(id);
        event.successEvent();
    }

}
