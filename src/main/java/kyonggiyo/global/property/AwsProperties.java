package kyonggiyo.global.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
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

}
