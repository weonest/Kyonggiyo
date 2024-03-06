package kyonggiyo.adapter.out.client.image;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(S3ImageUploaderTest.S3MockConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class S3ImageUploaderTest {

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
    private AmazonS3 amazonS3;

    @Autowired
    private ImageUploader imageUploader;

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
    void 이미지를_S3_버킷에_업로드한다() {
        // given
        MockMultipartFile file = new MockMultipartFile("image", "image.png", MediaType.IMAGE_PNG_VALUE, "<<data>>".getBytes());

        // when
        List<String> keys = imageUploader.uploadImage(List.of(file));
        S3Object object = amazonS3.getObject(BUCKET_NAME, keys.get(0));

        // then
        assertThat(keys).hasSize(1);
        assertThat(object.getKey()).isEqualTo(keys.get(0));
    }

    @Test
    void 이미지를_S3_버킷에서_삭제한다() {
        // given
        MockMultipartFile file = new MockMultipartFile("image", "image.png", MediaType.IMAGE_PNG_VALUE, "<<data>>".getBytes());
        List<String> keys = imageUploader.uploadImage(List.of(file));

        // when
        imageUploader.deleteImage(keys.get(0));
        boolean objectExist = amazonS3.doesObjectExist(BUCKET_NAME, keys.get(0));
        // then
        assertThat(objectExist).isFalse();
    }

}
