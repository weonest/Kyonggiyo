package kyonggiyo.global.config;

import kyonggiyo.global.property.KakaoOAuthProperties;
import kyonggiyo.global.property.NaverOAuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {KakaoOAuthProperties.class, NaverOAuthProperties.class})
public class PropertiesConfig {
}
