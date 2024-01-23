package kyonggiyo.util;

import jakarta.validation.constraints.NotBlank;
import kyonggiyo.domain.auth.Platform;
import org.springframework.core.convert.converter.Converter;

public class PlatformConverter implements Converter<String, Platform> {

    @Override
    public Platform convert(@NotBlank String platform) {
        return Platform.from(platform);
    }

}
