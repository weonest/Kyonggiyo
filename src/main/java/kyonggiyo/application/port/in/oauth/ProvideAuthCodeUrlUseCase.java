package kyonggiyo.application.port.in.oauth;

import kyonggiyo.domain.auth.Platform;

import java.net.URI;

public interface ProvideAuthCodeUrlUseCase {

    URI provideUri(Platform platform);

}
