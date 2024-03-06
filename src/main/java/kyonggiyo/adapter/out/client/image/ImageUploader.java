package kyonggiyo.adapter.out.client.image;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageUploader {

    List<String> uploadImage(List<MultipartFile> multipartFiles);

    void deleteImage(String imageUrl);

}
