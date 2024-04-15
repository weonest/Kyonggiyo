package kyonggiyo.global.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import kyonggiyo.global.property.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AwsConfig {

    private final AwsProperties awsProperties;

    @Bean
    public AmazonS3 s3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
                awsProperties.getAccessKey(),
                awsProperties.getSecretKey());

        return AmazonS3ClientBuilder.standard()
                .withRegion(awsProperties.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

}
