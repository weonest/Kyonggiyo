package kyonggiyo.common.config;

import kyonggiyo.common.property.AwsProperties;
import kyonggiyo.common.property.JwtProperties;
import kyonggiyo.common.property.KakaoOAuthProperties;
import kyonggiyo.common.property.NaverOAuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {
        KakaoOAuthProperties.class,
        NaverOAuthProperties.class,
        AwsProperties.class,
        JwtProperties.class})
public class PropertiesConfig {
}
