package kyonggiyo.image.port.outbound;

import kyonggiyo.image.domain.entity.Image;
import kyonggiyo.image.domain.vo.ImageType;

import java.util.List;

public interface LoadImagePort {

    List<Image> findAllByImageTypeAndReferenceId(ImageType imageType, Long referenceId);

    List<Image> findAllByImageTypeAndReferenceIdIn(ImageType imageType, List<Long> ids);
}
