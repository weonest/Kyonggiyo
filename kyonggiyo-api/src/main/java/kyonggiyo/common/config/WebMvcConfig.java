package kyonggiyo.common.config;

import kyonggiyo.domain.auth.util.PlatformConverter;
import kyonggiyo.domain.candidate.util.StatusConverter;
import kyonggiyo.auth.AuthorizationInterceptor;
import kyonggiyo.auth.AuthorizedArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "web-application", havingValue = "true")
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;
    private final AuthorizedArgumentResolver authorizedArgumentResolver;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new PlatformConverter());
        registry.addConverter(new StatusConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authorizedArgumentResolver);
    }

}
