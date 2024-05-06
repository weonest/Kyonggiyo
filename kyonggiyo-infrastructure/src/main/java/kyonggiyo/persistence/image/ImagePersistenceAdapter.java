package kyonggiyo.persistence.image;

import kyonggiyo.image.port.outbound.DeleteImagePort;
import kyonggiyo.image.port.outbound.LoadImagePort;
import kyonggiyo.image.port.outbound.SaveImagePort;
import kyonggiyo.image.domain.entity.Image;
import kyonggiyo.image.domain.vo.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ImagePersistenceAdapter implements SaveImagePort, LoadImagePort, DeleteImagePort {

    private final ImageRepository imageRepository;
    private final ImageBatchRepository imageBatchRepository;

    @Override
    public List<Image> saveAll(List<Image> images) {
        return imageRepository.saveAll(images);
    }

    @Override
    public void saveAllInBatch(List<Image> images) {
        imageBatchRepository.saveAllInBatch(images);
    }

    @Override
    public List<Image> findAllByImageTypeAndReferenceId(ImageType imageType, Long referenceId) {
        return imageRepository.findByImageTypeAndReferenceId(imageType, referenceId);
    }

    @Override
    public List<Image> findAllByImageTypeAndReferenceIdIn(ImageType imageType, List<Long> ids) {
        return imageRepository.findByImageTypeAndReferenceIdIn(imageType, ids);
    }

    @Override
    public void deleteById(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public void deleteAllByIdInBatch(List<Long> ids) {
        imageRepository.deleteAllByIdInBatch(ids);
    }

}
