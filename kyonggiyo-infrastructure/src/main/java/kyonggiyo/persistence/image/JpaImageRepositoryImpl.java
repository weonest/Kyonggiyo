package kyonggiyo.persistence.image;

import kyonggiyo.image.domain.entity.Image;
import kyonggiyo.image.domain.vo.ImageType;
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
    public List<Image> findByImageTypeAndReferenceIdIn(ImageType imageType, List<Long> ids) {
        return imageJpaRepository.findByImageTypeAndReferenceIdIn(imageType, ids);
    }

    @Override
    public void deleteById(Long id) {
        imageJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAllByIdInBatch(List<Long> ids) {
        imageJpaRepository.deleteAllByIdInBatch(ids);
    }

}

