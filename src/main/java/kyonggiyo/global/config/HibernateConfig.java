package kyonggiyo.global.config;

import kyonggiyo.global.logging.MDCRequestHandlerCommentInterceptor;
import kyonggiyo.global.logging.MdcLoggingFilter;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ConditionalOnBean(MdcLoggingFilter.class)
public class HibernateConfig {

    private final MDCRequestHandlerCommentInterceptor mdcRequestHandlerCommentInterceptor;

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertyConfig() {
        return hibernateProperties ->
                hibernateProperties.put(AvailableSettings.STATEMENT_INSPECTOR, mdcRequestHandlerCommentInterceptor);
    }

}
