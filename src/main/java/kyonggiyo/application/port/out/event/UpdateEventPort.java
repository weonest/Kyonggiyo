package kyonggiyo.application.port.out.event;

import java.util.List;

public interface UpdateEventPort {

    void updateStatusIdIn(List<Long> ids);

}
