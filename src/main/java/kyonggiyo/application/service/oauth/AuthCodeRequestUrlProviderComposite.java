package kyonggiyo.application.service.oauth;

import kyonggiyo.application.port.in.auth.ProvideAuthCodeUrlUseCase;
import kyonggiyo.domain.auth.Platform;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AuthCodeRequestUrlProviderComposite implements ProvideAuthCodeUrlUseCase {

    private final Map<Platform, AuthCodeRequestUrlProvider> providerMap;

    public AuthCodeRequestUrlProviderComposite(Set<AuthCodeRequestUrlProvider> providers) {
        this.providerMap = providers.stream()
                .collect(Collectors.toUnmodifiableMap(
                        AuthCodeRequestUrlProvider::getPlatform,
                        Function.identity()
                )
        );
    }

    @Override
    public URI provideUri(Platform platform) {
        return providerMap.get(platform).provideUri();
    }

}
