package kyonggiyo.image.service;

import kyonggiyo.image.port.inbound.DeleteImageUseCase;
import kyonggiyo.image.dto.ImageDeleteCommand;
import kyonggiyo.image.port.outbound.DeleteImagePort;
import kyonggiyo.image.port.outbound.ImageManager;
import kyonggiyo.image.port.outbound.LoadImagePort;
import kyonggiyo.image.port.outbound.SaveImagePort;
import kyonggiyo.image.domain.entity.Image;
import kyonggiyo.image.domain.vo.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements DeleteImageUseCase {

    private final ImageManager imageManager;
    private final SaveImagePort saveImagePort;
    private final LoadImagePort loadImagePort;
    private final DeleteImagePort deleteImagePort;

    @Transactional
    public void createImages(List<String> imageUrls, ImageType imageType, Long referenceId) {
        List<Image> images = imageUrls.stream()
                .map(v -> {
                    String key = imageManager.extractImageKey(v);
                    return new Image(key, imageType, referenceId);
                })
                .toList();
        saveImagePort.saveAll(images);
    }

    @Override
    @Transactional
    public void deleteById(ImageDeleteCommand command) {
        deleteImagePort.deleteById(command.id());
        imageManager.deleteImage(command.key());
    }

    @Transactional
    public void deleteByImageTypeAndReferenceId(ImageType imageType, Long referenceId) {
        List<Image> images = loadImagePort.findAllByImageTypeAndReferenceId(imageType, referenceId);
        List<Long> ids = images.stream()
                .map(Image::getId).toList();

        deleteImagePort.deleteAllByIdInBatch(ids);
    }

}
