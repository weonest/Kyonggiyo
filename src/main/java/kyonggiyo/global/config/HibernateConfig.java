package kyonggiyo.global.config;

import kyonggiyo.global.logging.MDCRequestHandlerCommentInterceptor;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "web-application", havingValue = "true")
public class HibernateConfig {

    private final MDCRequestHandlerCommentInterceptor mdcRequestHandlerCommentInterceptor;

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertyConfig() {
        return hibernateProperties ->
                hibernateProperties.put(AvailableSettings.STATEMENT_INSPECTOR, mdcRequestHandlerCommentInterceptor);
    }

}
