package kyonggiyo.application.port.out.image;

import kyonggiyo.domain.image.Image;

import java.util.List;

public interface SaveImagePort {

    List<Image> saveAll(List<Image> images);

    void saveAllInBatch(List<Image> images);

}
