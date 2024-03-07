package kyonggiyo.adapter.out.persistence.image;

import kyonggiyo.application.port.out.image.DeleteImagePort;
import kyonggiyo.application.port.out.image.GetImagePort;
import kyonggiyo.application.port.out.image.SaveImagePort;
import kyonggiyo.domain.image.Image;
import kyonggiyo.domain.image.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ImagePersistenceAdapter implements SaveImagePort, GetImagePort, DeleteImagePort {

    private final ImageRepository imageRepository;

    @Override
    public List<Image> saveAll(List<Image> images) {
        return imageRepository.saveAll(images);
    }

    @Override
    public List<Image> findAllByImageTypeAndReferenceId(ImageType imageType, Long referenceId) {
        return imageRepository.findByImageTypeAndReferenceId(imageType, referenceId);
    }

    @Override
    public void deleteAllByIdInBatch(List<Long> ids) {
        imageRepository.deleteAllByIdInBatch(ids);
    }

}