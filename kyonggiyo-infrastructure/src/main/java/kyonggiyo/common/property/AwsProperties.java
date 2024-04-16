package kyonggiyo.common.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@ConfigurationProperties("cloud.aws")
public class AwsProperties {

    private final String region;
    private final Credentials credentials;
    private final S3 s3;

    @Getter
    @RequiredArgsConstructor
    public static final class Credentials{
        private final String accessKey;
        private final String secretKey;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class S3{
        private final String bucketName;
    }

    public String getRegion() {
        return region;
    }

    public String getAccessKey() {
        return credentials.getAccessKey();
    }

    public String getSecretKey() {
        return credentials.getSecretKey();
    }

    public String getBucketName() {
        return s3.getBucketName();
    }

}
