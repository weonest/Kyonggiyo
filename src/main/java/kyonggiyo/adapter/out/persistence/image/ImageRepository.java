package kyonggiyo.adapter.out.persistence.image;

import kyonggiyo.domain.image.Image;
import kyonggiyo.domain.image.ImageType;

import java.util.List;

public interface ImageRepository {

    List<Image> saveAll(List<Image> images);

    List<Image> findByImageTypeAndReferenceId(ImageType imageType, Long referenceId);

    void deleteById(Long id);

    void deleteAllByIdInBatch(List<Long> ids);

}
