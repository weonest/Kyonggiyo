package kyonggiyo.application.service.image;

import kyonggiyo.adapter.out.image.ImageUploader;
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

    @Transactional
    public void createImage(List<MultipartFile> multipartFiles, ImageType imageType, Long referenceId) {
        List<String> urls = imageUploader.uploadImage(multipartFiles);
        List<Image> images = urls.stream()
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
            imageUploader.deleteImage(image.getImageUrl());
        }
    }

}
