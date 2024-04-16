package kyonggiyo.domain.auth.util;

import kyonggiyo.domain.auth.Platform;
import org.springframework.core.convert.converter.Converter;

public class PlatformConverter implements Converter<String, Platform> {

    @Override
    public Platform convert(String platform) {
        return Platform.from(platform);
    }

}
