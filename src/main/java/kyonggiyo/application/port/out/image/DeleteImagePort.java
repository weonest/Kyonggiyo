package kyonggiyo.application.port.out.image;

import java.util.List;

public interface DeleteImagePort {

    void deleteAllByIdInBatch(List<Long> ids);

}
