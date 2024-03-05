package kyonggiyo.adapter.out.image;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kyonggiyo.domain.image.exception.ImageErrorCode;
import kyonggiyo.domain.image.exception.ImageException;
import kyonggiyo.global.property.AwsProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageUploader {

    public static final String UPLOAD_PATH_FORMAT = "public/{0}_{1}";

    private final AmazonS3Client s3Client;
    private final AwsProperties awsProperties;

    public List<String> uploadImage(List<MultipartFile> multipartFiles) {
        List<String> urls = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            validateFileExtension(multipartFile);
            try{
                PutObjectRequest putObjectRequest = getPutObjectRequest(multipartFile);
                s3Client.putObject(putObjectRequest);
                urls.add(
                        s3Client.getUrl(
                                awsProperties.getBucketName(), putObjectRequest.getKey())
                                .toString());
            } catch (Exception e) {
                log.warn(" S3Client 예외 발생 " ,e);
                throw new ImageException(ImageErrorCode.UPLOAD_EXCEPTION);
            }
        }
        return urls;
    }

    public void deleteImage(String imageUrl) {
        s3Client.deleteObject(awsProperties.getBucketName(), imageUrl);
    }

    private void validateFileExtension(MultipartFile multipartFile) {
        String fileExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        ImageFileExtension.findExtension(fileExtension);
    }

    private PutObjectRequest getPutObjectRequest(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String imageUrl = getUploadPath(originalFilename);
        InputStream inputStream = multipartFile.getInputStream();
        ObjectMetadata objectMetaData = getObjectMetaData(multipartFile);

        return new PutObjectRequest(
                awsProperties.getBucketName(),
                imageUrl,
                inputStream,
                objectMetaData
        );
    }

    private String getUploadPath(String originalFilename) {
        return MessageFormat.format(UPLOAD_PATH_FORMAT, UUID.randomUUID(), originalFilename);
    }

    private ObjectMetadata getObjectMetaData(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        String fileExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(ImageFileExtension.findExtension(fileExtension));
        return objectMetadata;
    }

    @Getter
    enum ImageFileExtension {
        SVG("svg"),
        PNG("png"),
        JPG("jpg"),
        JPEG("jpeg"),
        ;

        private final String extension;

        ImageFileExtension(String extension) {
            this.extension = extension;
        }

        public static String findExtension(String fileExtension) {
            ImageFileExtension imageFileExtension = Arrays.stream(ImageFileExtension.values())
                    .filter(v -> v.getExtension().equals(fileExtension.toUpperCase()))
                    .findAny()
                    .orElseThrow(() -> new ImageException(ImageErrorCode.INVALID_FILE_EXTENSION));
            return imageFileExtension.getExtension();
        }
    }

}
