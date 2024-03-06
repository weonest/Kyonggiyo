package kyonggiyo.application.service.image;

import kyonggiyo.adapter.out.client.image.ImageUploader;
import kyonggiyo.application.port.out.image.DeleteImagePort;
import kyonggiyo.application.port.out.image.GetImagePort;
import kyonggiyo.application.port.out.image.SaveImagePort;
import kyonggiyo.domain.image.Image;
import kyonggiyo.domain.image.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageUploader imageUploader;
    private final SaveImagePort saveImagePort;
    private final GetImagePort getImagePort;
    private final DeleteImagePort deleteImagePort;

    // TODO: 2024-03-06 ReviewCommandService의 트랜잭션에서 계속 이어지고 있음. 테스트 후에 분리해보기
    @Transactional
    public void createImage(List<MultipartFile> multipartFiles, ImageType imageType, Long referenceId) {
        List<String> keys = imageUploader.uploadImage(multipartFiles);
        List<Image> images = keys.stream()
                .map(v -> new Image(v, imageType, referenceId)).toList();
        saveImagePort.saveAll(images);
    }

    @Transactional
    public void deleteImage(ImageType imageType, Long referenceId) {
        List<Image> images = getImagePort.findAllByImageTypeAndReferenceId(imageType, referenceId);
        List<Long> ids = images.stream()
                .map(Image::getId).toList();

        deleteImagePort.deleteAllByIdInBatch(ids);
        for (Image image : images) {
            imageUploader.deleteImage(image.getKey());
        }
    }

}
