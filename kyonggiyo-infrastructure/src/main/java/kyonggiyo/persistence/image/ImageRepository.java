package kyonggiyo.persistence.image;

import kyonggiyo.image.domain.entity.Image;
import kyonggiyo.image.domain.vo.ImageType;

import java.util.List;

public interface ImageRepository {

    List<Image> saveAll(List<Image> images);

    List<Image> findByImageTypeAndReferenceId(ImageType imageType, Long referenceId);

    List<Image> findByImageTypeAndReferenceIdIn(ImageType imageType, List<Long> ids);

    void deleteById(Long id);

    void deleteAllByIdInBatch(List<Long> ids);

}
