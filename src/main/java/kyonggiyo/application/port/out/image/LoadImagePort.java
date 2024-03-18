package kyonggiyo.application.port.out.image;

import kyonggiyo.domain.image.Image;
import kyonggiyo.domain.image.ImageType;

import java.util.List;

public interface LoadImagePort {

    List<Image> findAllByImageTypeAndReferenceId(ImageType imageType, Long referenceId);

}
