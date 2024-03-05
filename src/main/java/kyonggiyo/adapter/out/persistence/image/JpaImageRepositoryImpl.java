package kyonggiyo.adapter.out.persistence.image;

import kyonggiyo.domain.image.Image;
import kyonggiyo.domain.image.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaImageRepositoryImpl implements ImageRepository {

    private final ImageJpaRepository imageJpaRepository;

    @Override
    public List<Image> saveAll(List<Image> images) {
        return imageJpaRepository.saveAll(images);
    }

    @Override
    public List<Image> findByImageTypeAndReferenceId(ImageType imageType, Long referenceId) {
        return imageJpaRepository.findByImageTypeAndReferenceId(imageType, referenceId);
    }

    @Override
    public void deleteAllByIdInBatch(List<Long> ids) {
        imageJpaRepository.deleteAllByIdInBatch(ids);
    }

}

