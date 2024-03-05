package kyonggiyo.adapter.out.persistence.image;

import kyonggiyo.domain.image.Image;
import kyonggiyo.domain.image.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {

    List<Image> findByImageTypeAndReferenceId(ImageType imageType, Long referenceId);

}
