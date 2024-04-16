package kyonggiyo.auth.provider;

import kyonggiyo.domain.auth.Platform;
import lombok.Getter;

import java.net.URI;

@Getter
public abstract class AuthCodeRequestUrlProvider {

    protected Platform platform;

    public AuthCodeRequestUrlProvider(Platform platform) {
        this.platform = platform;
    }

    public abstract URI provideUri();

}
