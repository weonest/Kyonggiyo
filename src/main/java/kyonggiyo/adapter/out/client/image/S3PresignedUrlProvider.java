package kyonggiyo.adapter.out.client.image;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import kyonggiyo.domain.image.exception.ImageErrorCode;
import kyonggiyo.domain.image.exception.ImageException;
import kyonggiyo.global.property.AwsProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.random.RandomGenerator;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3PresignedUrlProvider implements PresignedUrlProvider {

    public static final String UPLOAD_PATH_FORMAT = "public/{0}_{1}";
    private static final String URL_PATH = "https://kyonggiyo-bucket.s3.ap-northeast-2.amazonaws.com/";

    private final AmazonS3 s3Client;
    private final AwsProperties awsProperties;

    @Override
    public String generatePresignedUrl(String filename) {
        validateFileExtension(filename);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(filename);

        return generatePresignedUrl(generatePresignedUrlRequest);
    }

    private void validateFileExtension(String filename) {
        String fileExtension = StringUtils.getFilenameExtension(filename);
        ImageFileExtension.findExtension(fileExtension);
    }

    private GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String filename) {
        String key = generateImageKey(filename);

        return new GeneratePresignedUrlRequest(awsProperties.getBucketName(), key)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getPresignedExpiraton());
    }

    private String generateImageKey(String filename) {
        int identifier = RandomGenerator.getDefault().nextInt();
        return MessageFormat.format(UPLOAD_PATH_FORMAT, identifier, filename);
    }

    private Date getPresignedExpiraton() {
        Date expiration = new Date();
        long expirationMillis = expiration.getTime();
        expirationMillis += 1000 * 60 * 2;
        expiration.setTime(expirationMillis);
        return expiration;
    }

    private String generatePresignedUrl(GeneratePresignedUrlRequest request) {
        try {
            return s3Client.generatePresignedUrl(request).toString();
        } catch (AmazonServiceException e) {
            log.warn("PresignedUrl 생성에 실패하였습니다. {}", e.getMessage());
            throw new ImageException(ImageErrorCode.UPLOAD_EXCEPTION);
        }
    }

    @Override
    public String extractImageKey(String imageUrl) {
        if (imageUrl == null) {
            return null;
        }
        return imageUrl.replace(URL_PATH, "");
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
                    .filter(v -> v.getExtension().equals(fileExtension.toLowerCase()))
                    .findAny()
                    .orElseThrow(() -> new ImageException(ImageErrorCode.INVALID_FILE_EXTENSION));
            return imageFileExtension.getExtension();
        }
    }

}
