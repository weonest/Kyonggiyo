package kyonggiyo.image.port.outbound;

import java.util.List;

public interface DeleteImagePort {

    void deleteById(Long id);

    void deleteAllByIdInBatch(List<Long> ids);

}
