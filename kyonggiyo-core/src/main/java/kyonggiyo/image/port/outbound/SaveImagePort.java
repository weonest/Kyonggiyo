package kyonggiyo.image.port.outbound;

import kyonggiyo.image.domain.entity.Image;

import java.util.List;

public interface SaveImagePort {

    List<Image> saveAll(List<Image> images);

    void saveAllInBatch(List<Image> images);

}
