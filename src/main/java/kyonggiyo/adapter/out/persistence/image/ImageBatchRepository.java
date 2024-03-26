package kyonggiyo.adapter.out.persistence.image;

import kyonggiyo.domain.image.Image;

import java.util.List;

public interface ImageBatchRepository {

    void saveAllInBatch(List<Image> images);

}
