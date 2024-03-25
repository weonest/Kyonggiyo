package kyonggiyo.application.service.image;

import kyonggiyo.adapter.out.client.image.PresignedUrlProvider;
import kyonggiyo.application.port.in.image.DeleteImageUseCase;
import kyonggiyo.application.port.out.image.DeleteImagePort;
import kyonggiyo.application.port.out.image.LoadImagePort;
import kyonggiyo.application.port.out.image.SaveImagePort;
import kyonggiyo.domain.image.Image;
import kyonggiyo.domain.image.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements DeleteImageUseCase {

    private final PresignedUrlProvider presignedUrlProvider;
    private final SaveImagePort saveImagePort;
    private final LoadImagePort loadImagePort;
    private final DeleteImagePort deleteImagePort;

    @Transactional
    public void createImage(List<String> imageUrls, ImageType imageType, Long referenceId) {
        List<Image> images = imageUrls.stream()
                .map(v -> {
                    String key = presignedUrlProvider.extractImageKey(v);
                    return new Image(key, imageType, referenceId);
                })
                .toList();
        saveImagePort.saveAll(images);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        deleteImagePort.deleteById(id);
    }

    @Transactional
    public void deleteByImageTypeAndReferenceId(ImageType imageType, Long referenceId) {
        List<Image> images = loadImagePort.findAllByImageTypeAndReferenceId(imageType, referenceId);
        List<Long> ids = images.stream()
                .map(Image::getId).toList();

        deleteImagePort.deleteAllByIdInBatch(ids);
    }

}
