package adapter.out.client.image;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import static org.assertj.core.api.Assertions.assertThat;

@Import(S3ImageManagerTest.S3MockConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class S3ImageManagerTest {

    public static final String BUCKET_NAME = "kyonggiyo-bucket";

    @TestConfiguration
    public static class S3MockConfig {
        private static final int PORT = 8001;

        @Bean
        public S3Mock s3Mock() {
            return new S3Mock.Builder()
                    .withPort(PORT)
                    .withInMemoryBackend()
                    .build();
        }

        @Bean
        @Primary
        public AmazonS3 amazonS3() {
            AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
                    "http://127.0.0.1:" + PORT,
                    Regions.AP_NORTHEAST_1.name());

            return AmazonS3ClientBuilder
                    .standard()
                    .withPathStyleAccessEnabled(true)
                    .withEndpointConfiguration(endpoint)
                    .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                    .build();
        }

    }

    @Autowired
    private ImageManager imageManager;

    @BeforeAll
    static void beforeAll(@Autowired S3Mock s3Mock, @Autowired AmazonS3 s3Client) {
        s3Mock.start();
        s3Client.createBucket(BUCKET_NAME);
    }

    @AfterAll
    static void afterAll(@Autowired S3Mock s3Mock, @Autowired AmazonS3 s3Client) {
        s3Client.shutdown();
        s3Mock.stop();
    }

    @Test
    void 파일명을_통해_presignedUrl을_생성한다() {
        // given
        String filename = "image.jpg";

        // when
        String presignedUrl = imageManager.generatePresignedUrl(filename);

        // then
        assertThat(presignedUrl).isNotNull();
    }

}
