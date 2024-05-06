package kyonggiyo.auth.port.inbound;

import kyonggiyo.auth.domain.vo.Platform;

import java.net.URI;

public interface ProvideAuthCodeUrlUseCase {

    URI provideUri(Platform platform);

}
